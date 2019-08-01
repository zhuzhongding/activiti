package cn.itcast.a_helloworld;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class HelloWorld {
    
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
    /**�������̶���**/
    @Test
    public void deployProcessDefinition(){
        Deployment deployment = processEngine.getRepositoryService()//�����̶���Ͳ��������ص�service
                       .createDeployment()//����һ���������
                       .name("helloworld���ų���")//��ӵĲ�������
                       .addClasspathResource("diagrams/helloworld.bpmn")//��classpath��Դ�м��أ�һ��ֻ�ܼ���һ���ļ�
                       .addClasspathResource("diagrams/helloworld.png")//��classpath��Դ�м��أ�һ��ֻ�ܼ���һ���ļ�
                       .deploy();//��ɲ���
        System.out.println("����ID:"+deployment.getId());//1
        System.out.println("�������ƣ�"+deployment.getName());//helloworld���ų���
    }
    
    /**��������ʵ��**/
    @Test
    public void startProcessInstance(){
        //���̶����key
        String processDefinitionKey = "helloworld";
        ProcessInstance processInstance = processEngine.getRuntimeService()//������ִ�е�����ʵ�������̶�����ص�service
                     .startProcessInstanceByKey(processDefinitionKey);//ʹ�����̶����key��������ʵ����key����helloworld.bpmn�ļ��е�id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
        System.out.println("����ʵ��ID:"+processInstance.getId());//����ʵ��ID:101
        System.out.println("���̶���ID:"+processInstance.getProcessDefinitionId());//���̶���ID:helloworld:1:4
    }
    
    /**��ѯ��ǰ�˵ĸ�������**/
    @Test
    public void findMyPersonalTask(){
        String assignee = "����";
        List<Task> list = processEngine.getTaskService()
                        .createTaskQuery()
                        .taskAssignee(assignee)
                        .list();
        if(list != null && list.size() >0) {
            for (Task task : list) {
                System.out.println("����ID:"+task.getId());
                System.out.println("��������:"+task.getName());
                System.out.println("����Ĵ���ʱ��:"+task.getCreateTime());
                System.out.println("����İ�����:"+task.getAssignee());
                System.out.println("����ʵ��ID:"+task.getProcessInstanceId());
                System.out.println("ִ�ж���ID:"+task.getExecutionId());
                System.out.println("���̶���ID:"+task.getProcessDefinitionId());
                System.out.println("#########################################");
            }
        }
    }
    
    /**����ҵ�����**/
    @Test
    public void comMyPersonalTask() {
        String taskId = "11902";
        processEngine.getTaskService()
                    .complete(taskId);
        System.out.println("�����������ID:"+taskId);
    }
    
    
}
