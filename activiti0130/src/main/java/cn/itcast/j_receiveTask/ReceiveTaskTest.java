package cn.itcast.j_receiveTask;

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
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ReceiveTaskTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
    /**�������̶��壨inputStream��**/ 
    @Test
    public void deployProcessDefinition_inputStream(){
        InputStream inputStreamBpmn  = this.getClass().getResourceAsStream("receiveTask.bpmn");
        InputStream inputStreamPng  = this.getClass().getResourceAsStream("receiveTask.png");
        Deployment deployment = processEngine.getRepositoryService()//�����̶���Ͳ��������ص�service
                       .createDeployment()//����һ���������
                       .name("���ջ����")//��ӵĲ�������
                       .addInputStream("receiveTask.bpmn", inputStreamBpmn)
                       .addInputStream("receiveTask.png", inputStreamPng)
                       .deploy();//��ɲ���
        System.out.println("����ID:"+deployment.getId());//
        System.out.println("�������ƣ�"+deployment.getName());//
    }
    
    /**��������ʵ��+�������̱���+��ȡ���̱���+���ִ��һ��*/
    @Test
    public void startProcessInstance(){
        //���̶����key
        String processDefinitionKey = "receiveTask";
        ProcessInstance processInstance = processEngine.getRuntimeService()//������ִ�е�����ʵ�������̶�����ص�service
                     .startProcessInstanceByKey(processDefinitionKey);//ʹ�����̶����key��������ʵ����key����helloworld.bpmn�ļ��е�id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
        System.out.println("����ʵ��ID:"+processInstance.getId());//����ʵ��ID:101
        System.out.println("���̶���ID:"+processInstance.getProcessDefinitionId());//���̶���ID:helloworld:1:4
        
        /**��ѯִ�ж���processInstance.getId()*/
        Execution execution1 = processEngine.getRuntimeService()
                        .createExecutionQuery()
                        .processInstanceId(processInstance.getId())
                        .activityId("receivetask1")
                        .singleResult();
        /**ʹ�����̱������õ������۶��������ҵ�����*/
        processEngine.getRuntimeService()
                        .setVariable(execution1.getId(), "���ܵ������۶�", 21000);
        
        /**���ִ��һ����������̴��ڵȴ�״̬��ʹ�����̼���ִ��*/
        processEngine.getRuntimeService()
                        .signal(execution1.getId());
        
        /**��ѯִ�ж���*/
        Execution execution2 = processEngine.getRuntimeService()
                        .createExecutionQuery()
                        .processInstanceId(processInstance.getId())
                        .activityId("receivetask2")
                        .singleResult();
        /**�����̱����л�ȡ���ܵ������۶��ֵ*/
        Integer value = (Integer)processEngine.getRuntimeService()
                        .getVariable(execution2.getId(), "���ܵ������۶�");
        System.out.println("���ϰ巢�Ͷ��ţ�����ǣ�"+value);
        /**���ִ��һ����������̴��ڵȴ�״̬��ʹ�����̼���ִ��*/
        processEngine.getRuntimeService()
                        .signal(execution2.getId());
        
    }
    
}
