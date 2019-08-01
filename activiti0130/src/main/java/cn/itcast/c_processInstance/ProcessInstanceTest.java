package cn.itcast.c_processInstance;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ProcessInstanceTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
    /**�������̶��壨��zip��**/ 
    @Test
    public void deployProcessDefinition_zip(){
        InputStream in  = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deployment = processEngine.getRepositoryService()//�����̶���Ͳ��������ص�service
                       .createDeployment()//����һ���������
                       .name("���̶���")//��ӵĲ�������
                       .addZipInputStream(zipInputStream)
                       .deploy();//��ɲ���
        System.out.println("����ID:"+deployment.getId());//
        System.out.println("�������ƣ�"+deployment.getName());//
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
        String assignee = "王五";
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
        String taskId = "13502";
        processEngine.getTaskService()
                    .complete(taskId);
        System.out.println("�����������ID:"+taskId);
    }
    
    /**��ѯ����״̬*/
    @Test
    public void isProcessEnd() {
        String processInstanceId = "1001";
        ProcessInstance singleResult = processEngine.getRuntimeService()
                        .createProcessInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .singleResult();
        if(singleResult == null) {
            System.out.println("�����Ѿ�����");
        }
        else{
            System.out.println("����û�н���");
        }
    }    
    
    /**��ѯ��ʷ����(���潲)*/
    @Test
    public void findHistoryTask() {
        String taskAssignee = "����";
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                        .createHistoricTaskInstanceQuery()
                        .taskAssignee(taskAssignee)
                        .list();
        if(list != null && list.size() >0) {
            for (HistoricTaskInstance hit : list) {
                System.out.println(hit.getId()+" "+hit.getName()+" "+hit.getProcessInstanceId()+" "+hit.getStartTime()+" "+hit.getEndTime()+" "+hit.getDurationInMillis());
                System.out.println("###############################");
            }
        }
    }
    
    /**��ѯ��ʷ����ʵ�������潲��*/
    @Test
    public void findHistoryProcessInstance() {
        String processInstanceId = "1001";
        HistoricProcessInstance instance = processEngine.getHistoryService()
                        .createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .singleResult();
        System.out.println(instance.getId()+"  "+instance.getProcessDefinitionId()+"  "+instance.getStartTime()+"  "+instance.getEndTime()+"  "+instance.getDurationInMillis());
    }
    
    
    
}
