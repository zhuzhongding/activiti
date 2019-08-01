package cn.itcast.l_group;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
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
//        Map<String,Object> variables = new HashMap<String,Object>();
//        variables.put("userId", "������");
        ProcessInstance processInstance = processEngine.getRuntimeService()//������ִ�е�����ʵ�������̶�����ص�service
                     .startProcessInstanceByKey(processDefinitionKey);//ʹ�����̶����key��������ʵ����key����helloworld.bpmn�ļ��е�id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
        System.out.println("����ʵ��ID:"+processInstance.getId());//����ʵ��ID:101
        System.out.println("���̶���ID:"+processInstance.getProcessDefinitionId());//���̶���ID:helloworld:1:4
    }
    
    /**��ѯ��ǰ�˵ĸ�������**/
    @Test
    public void findMyPersonalTask(){
        String assignee = "��F";
        List<Task> list = processEngine.getTaskService()
                        .createTaskQuery()
                        //��ѯ����where����
                        .taskAssignee(assignee)
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
    
    /**��ѯ��ǰ�˵�������**/
    @Test
    public void findMyGroupTask(){
        String candidateUser = "СA";
        List<Task> list = processEngine.getTaskService()
                        .createTaskQuery()
                        //��ѯ����where����
//                        .taskAssignee(assignee)
//                        .processInstanceId(processInstanceId)//ʹ������ʵ��ID��ѯ
//                        .processDefinitionId(processDefinitionId)//ʹ�����̶���ID��ѯ
                        .taskCandidateUser(candidateUser)//������İ����˲�ѯ
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
        String taskId = "7604";
//        Map<String,Object> variables =new HashMap<String,Object>();
//        variables.put("message", "��Ҫ");
        processEngine.getTaskService()
                    .complete(taskId);
        System.out.println("�����������ID:"+taskId);
    }
    
    /**��ѯ����ִ�е���������˱�*/
    @Test
    public void findRunPersonTask() {
        String taskId = "7604";
        List<IdentityLink> list = processEngine.getTaskService()
                        .getIdentityLinksForTask(taskId);
        if(list != null && list.size() >0) {
            for (IdentityLink identityLink : list) {
                System.out.println(identityLink.getTaskId()+"   "+identityLink.getType()+"   "+identityLink.getProcessInstanceId()+"   "+identityLink.getUserId());
            }
        }
    }
    
    /**��ѯ��ʷ����İ����˱�*/
    @Test
    public void findHistoryPersonTask() {
        //����ʵ��
        String processInstanceId = "7601";
        List<HistoricIdentityLink> list = processEngine.getHistoryService()
                        .getHistoricIdentityLinksForProcessInstance(processInstanceId);
        if(list != null && list.size() >0) {
            for (HistoricIdentityLink historicIdentityLink : list) {
                System.out.println(historicIdentityLink.getTaskId()+"  "+historicIdentityLink.getType()+"  "+historicIdentityLink.getUserId()+"  "+historicIdentityLink.getProcessInstanceId());
            }
        }
    }
    
    /**ʰȡ���񣬽�������������������ָ������İ������ֶ�*/
    @Test
    public void claim() {
        String taskId = "7604";
        String userId = "СA";
        //����ĸ������񣨿������������еĳ�Ա��Ҳ�����Ƿ�������ĳ�Ա��
        processEngine.getTaskService()
                        .claim(taskId, userId);
    }
    
    /**������������˵�������*/
    @Test
    public void setAssignee() {
        //����
        String taskId = "7604";
        processEngine.getTaskService()
                        .setAssignee(taskId, null);
    }
    
    /**������������ӳ�Ա*/
    @Test
    public void addGroupUser() {
        //����
        String taskId = "7604";
        //��Ա������
        String userId = "��H";
        processEngine.getTaskService()
                        .addCandidateUser(taskId, userId);
    }
    
    /**����������ɾ����Ա*/
    @Test
    public void deleteGroupUser() {
      //����
      String taskId = "7604";
      //��Ա������
      String userId = "СB";
      processEngine.getTaskService()
                      .deleteCandidateUser(taskId, userId);
    }
    
}
