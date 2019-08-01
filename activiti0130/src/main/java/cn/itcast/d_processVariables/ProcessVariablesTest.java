package cn.itcast.d_processVariables;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ProcessVariablesTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
    /**�������̶���**/
    @Test
    public void deployProcessDefinition_inputStream(){
        InputStream inputStreambpmn = this.getClass().getResourceAsStream("/diagrams/processVariables.bpmn");
        InputStream InputStreampng  = this.getClass().getResourceAsStream("/diagrams/processVariables.png");
        Deployment deployment = processEngine.getRepositoryService()//�����̶���Ͳ��������ص�service
                       .createDeployment()//����һ���������
                       .name("���̶���")//��ӵĲ�������
                       .addInputStream("processVariables.bpmn", inputStreambpmn)
                       .addInputStream("processVariables.png", InputStreampng)
                       .deploy();//��ɲ���
        System.out.println("����ID:"+deployment.getId());//1
        System.out.println("�������ƣ�"+deployment.getName());//helloworld���ų���
    }
    
    /**��������ʵ��**/
    @Test
    public void startProcessInstance(){
        //���̶����key
        String processDefinitionKey = "processVariables";
        ProcessInstance processInstance = processEngine.getRuntimeService()//������ִ�е�����ʵ�������̶�����ص�service
                     .startProcessInstanceByKey(processDefinitionKey);//ʹ�����̶����key��������ʵ����key����helloworld.bpmn�ļ��е�id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
        System.out.println("����ʵ��ID:"+processInstance.getId());//����ʵ��ID:101
        System.out.println("���̶���ID:"+processInstance.getProcessDefinitionId());//���̶���ID:helloworld:1:4
    }
    
    /**�������̱���*/
    @Test
    public void setVariables() {
        /**����������ִ�У�*/
        TaskService taskService = processEngine.getTaskService();
        //����ID
        String taskId = "2004";
        /**�������̱�����ʹ�û�����������*/
//        taskService.setVariableLocal(taskId, "�������", 3);
//        taskService.setVariable(taskId, "���ʱ��", new Date());
//        taskService.setVariable(taskId, "���ԭ��", "�ؼ�̽��");
        /**�������̱�����ʹ��javabean����*/
        /**
         * ��һ��javabean(ʵ�����к�)���õ����̱����У�Ҫ��javabean������ֵ����
         * ��������ı䣬�ٻ�ȡֵ��ʱ����׳��쳣
         * ���������������л�ID��private static final long serialVersionUID = 3816444255375694117L;
         * ͬʱʵ��Serializable
         * 
         */
        Person person = new Person();
        person.setId(10);
        person.setName("�仨");
        taskService.setVariable(taskId, "��Ա��Ϣ", person);
        
        
        System.out.println("�������̱����ɹ�");
    }
    
    @Test
    public void getVariables() {
        /**����������ִ�У�*/
        TaskService taskService = processEngine.getTaskService();
        //����ID
        String taskId = "2004";
        /**��ȡ���̱�����ʹ�û�����������*/
//        Integer days = (Integer)taskService.getVariable(taskId, "�������");
//        Date date = (Date)taskService.getVariable(taskId, "���ʱ��");
//        String reason = (String)taskService.getVariable(taskId, "���ԭ��");
//        System.out.println("���������"+days+",�������:"+date+",���ԭ��:"+reason);
        /**��ȡ���̱�����ʹ��javabean����*/
        Person person = (Person)taskService.getVariable(taskId, "��Ա��Ϣ");
        System.out.println(person.getId()+"   "+person.getName());
    }
    
    
    /**ģ�����úͻ�ȡ���̱����ĳ���*/
    @Test
    public void setAndGetVariables() {
        /**������ʵ����ִ�ж�������ִ�У�*/
        RuntimeService runtimeService = processEngine.getRuntimeService();
        
        /**����������ִ�У�*/
        TaskService taskService = processEngine.getTaskService();
        /**�������̱���*/
//        runtimeService.setVariable(executionId, variableName, value);//��ʾʹ��ִ�ж���ID�������̱��������ƣ��������̱�����ֵ��һ��ֻ������һ��ֵ��
//        runtimeService.setVariableLocal(executionId, variableName, value);//��ʾʹ��ִ�ж���ID,��Map�����������̱�����map���ϵ�key�������̱��������ƣ�map���ϵ�value�������̱�����ֵ
        
//        taskService.setVariable(taskId, variableName, value);//��ʾʹ������ID�������̱��������ƣ��������̱�����ֵ��һ��ֻ������һ��ֵ��
//        taskService.setVariables(taskId, variables);//��ʾʹ������ID����map�����������̱�����map���ϵ�key�������̱��������ƣ�map���ϵ�value�������̱�����ֵ
        
//        runtimeService.startProcessInstanceByKey(processDefinitionKey, variables)//��������ʵ����ͬʱ�������������̱�������map����
//        taskService.complete(taskId, variables);//��������ͬʱ���������̱�����ʹ��Map����
        
        /**��ȡ���̱���*/
//        runtimeService.getVariable(executionId, variableName);//ʹ��ִ�ж���ID�����̱������ƻ�ȡ���̱�����ֵ
//        runtimeService.getVariables(executionId);//ʹ��ִ�ж���ID��ȡ���е����̱����������̱����ŵ�map�����У�map���ϵ�key�������̱��������ƣ�map���ϵ�value�������̱�����ֵ
//        runtimeService.getVariables(executionId, variableNames);//ʹ��ִ�ж���ID��ȡ���̱�����ֵ��ͨ���������̱��������ƴ�ŵ������У���ȡָ�����̱�����ֵ
        
//      taskService.getVariable(taskId, variableName);//ʹ������ID�����̱������ƻ�ȡ���̱�����ֵ
//      taskService.getVariables(taskId);//ʹ������ID��ȡ���е����̱����������̱����ŵ�map�����У�map���ϵ�key�������̱��������ƣ�map���ϵ�value�������̱�����ֵ
//      taskService.getVariables(taskId, variableNames);//ʹ������ID��ȡ���̱�����ֵ��ͨ���������̱��������ƴ�ŵ������У���ȡָ�����̱�����ֵ
        
    }
    
    /**����ҵ�����**/
    @Test
    public void comMyPersonalTask() {
        String taskId = "2202";
        processEngine.getTaskService()
                    .complete(taskId);
        System.out.println("�����������ID:"+taskId);
    }
    
    /**��ѯ���̱�����ʷ��*/
    @Test
    public void findHistoryProcessVariables() {
          List<HistoricVariableInstance> list = processEngine.getHistoryService()
                          .createHistoricVariableInstanceQuery()
                          .variableName("�������")
                          .list();
        if(list != null && list.size() >0) {
            for (HistoricVariableInstance hvi : list) {
                System.out.println(hvi.getId()+"   "+hvi.getProcessInstanceId()+"   "+hvi.getVariableName()+"   "+hvi.getVariableTypeName()+"   "+hvi.getValue());
                System.out.println("###############################################");
            }
        }
        
    }
     
    
    
    
}
