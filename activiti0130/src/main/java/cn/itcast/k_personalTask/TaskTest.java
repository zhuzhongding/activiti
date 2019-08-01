package cn.itcast.k_personalTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class TaskTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
    /**�������̶��壨inputStream��**/ 
    @Test
    public void deployProcessDefinition_inputStream(){
        InputStream inputStreamBpmn  = this.getClass().getResourceAsStream("task.bpmn");
        InputStream inputStreamPng  = this.getClass().getResourceAsStream("task.png");
        Deployment deployment = processEngine.getRepositoryService()//�����̶���Ͳ��������ص�service
                       .createDeployment()//����һ���������
                       .name("����")//��ӵĲ�������
                       .addInputStream("task.bpmn", inputStreamBpmn)
                       .addInputStream("task.png", inputStreamPng)
                       .deploy();//��ɲ���
        System.out.println("����ID:"+deployment.getId());//
        System.out.println("�������ƣ�"+deployment.getName());//
    }
    
    /**��������ʵ��**/
    @Test
    public void startProcessInstance(){
        //���̶����key
        String processDefinitionKey = "task";
        /**��������ʵ����ͬʱ���������̱�����*/
        Map<String,Object> variables = new HashMap<String,Object>();
        variables.put("userId", "������");
        ProcessInstance processInstance = processEngine.getRuntimeService()//������ִ�е�����ʵ�������̶�����ص�service
                     .startProcessInstanceByKey(processDefinitionKey,variables);//ʹ�����̶����key��������ʵ����key����helloworld.bpmn�ļ��е�id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
        System.out.println("����ʵ��ID:"+processInstance.getId());//����ʵ��ID:101
        System.out.println("���̶���ID:"+processInstance.getProcessDefinitionId());//���̶���ID:helloworld:1:4
    }
    
    /**��ѯ��ǰ�˵ĸ�������**/
    @Test
    public void findMyPersonalTask(){
        String assignee = "������";
        List<Task> list = processEngine.getTaskService()
                        .createTaskQuery()
                        //��ѯ����where����
//                        .taskAssignee(assignee)
//                        .processInstanceId(processInstanceId)//ʹ������ʵ��ID��ѯ
//                        .processDefinitionId(processDefinitionId)//ʹ�����̶���ID��ѯ
//                        .taskCandidateUser(candidateUser)//������İ����˲�ѯ
//                        .executionId(executionId)//ʹ��ִ�ж���ID��ѯ
                        //����
                        .orderByTaskCreateTime().asc()//ʹ�ô���ʱ�����������
//                        .listPage(firstResult, maxResults)//��ҳ��ѯ
//                        .count()//���ؽ����������
//                        .singleResult()//����Ψһ�����
                        .list();//�����б�
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
        String taskId = "5205";
//        Map<String,Object> variables =new HashMap<String,Object>();
//        variables.put("message", "��Ҫ");
        processEngine.getTaskService()
                    .complete(taskId);
        System.out.println("�����������ID:"+taskId);
    }
    
    
}
