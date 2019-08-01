package cn.itcast.b_processDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ProcessDefinitionTest {
    
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
    /**�������̶���(��classpath)**/ 
    @Test
    public void deployProcessDefinition(){
        Deployment deployment = processEngine.getRepositoryService()//�����̶���Ͳ��������ص�service
                       .createDeployment()//����һ���������
                       .name("���̶���")//��ӵĲ�������
                       .addClasspathResource("diagrams/helloworld.bpmn")//��classpath��Դ�м��أ�һ��ֻ�ܼ���һ���ļ�
                       .addClasspathResource("diagrams/helloworld.png")//��classpath��Դ�м��أ�һ��ֻ�ܼ���һ���ļ�
                       .deploy();//��ɲ���
        System.out.println("����ID:"+deployment.getId());//
        System.out.println("�������ƣ�"+deployment.getName());//
    }
    
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
    
    /**��ѯ���̶���**/ 
    @Test
    public void findProcessDefinition(){
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                        .createProcessDefinitionQuery()//����һ�����̶���Ĳ�ѯ
                        /**ָ����ѯ����*/
//                        .deploymentId(deploymentId)//ʹ�ò������ID��ѯ
//                        .processDefinitionKey(processDefinitionKey)//ʹ�����̶����keyֵ��ѯ
//                        .processDefinitionId(processDefinitionId)//ʹ�����̶���ID��ѯ
//                        .processDefinitionNameLike(processDefinitionNameLike)//ʹ�����̶��������ģ����ѯ
                        
                        /**����*/
                        .orderByProcessDefinitionVersion().asc()//���հ汾����������
//                        .orderByProcessDefinitionName().desc()//�������̶�������ƽ�������
                        
                        /**���صĽ����*/
                        .list();//����һ�������б�,��װ���̶���
//                        .singleResult();//����Ψһ�����
//                        .count();//���ؽ��������
//                        .listPage(firstResult, maxResults);//��ҳ��ѯ
                        
        if(list != null && list.size() > 0) {
            for (ProcessDefinition processDefinition : list) {
                System.out.println("���̶���ID:"+processDefinition.getId());//���̶����key+�汾+���������
                System.out.println("���̶��������:"+processDefinition.getName());//��Ӧhelloworld.bpmn�ļ���name����ֵ
                System.out.println("���̶����key:"+processDefinition.getKey());//��Ӧhelloworld.bpmn�ļ���id����ֵ
                System.out.println("���̶���İ汾:"+processDefinition.getVersion());//�����̶����keyֵ��ͬ�������£��汾������Ĭ��1
                System.out.println("��Դ����bpmn�ļ�:"+processDefinition.getResourceName());
                System.out.println("��Դ����png�ļ�:"+processDefinition.getDiagramResourceName());
                System.out.println("�������ID:"+processDefinition.getDeploymentId());
                System.out.println("#######################################################");
            }
        }
                        
    }
    
    /**ɾ�����̶���**/ 
    @Test
    public void deleteProcessDefinition(){
        //ʹ�ò���ID���ɾ��
        String deploymentId = "12101";
        /**
         * ����������ɾ��
         * ֻ��ɾ��û�����������̣���������Ѿ����������׳��쳣
         */
//        processEngine.getRepositoryService()
//                        .deleteDeployment(deploymentId);
          processEngine.getRepositoryService()
                          .deleteDeployment(deploymentId, true);
        System.out.println("ɾ���ɹ���");
    }
    
    /**�鿴����ͼ
     * @throws IOException **/ 
    @Test
    public void viewPic() throws IOException{
        /**�������ļ��ŵ��ļ�����*/
        String deploymentId = "501";
        //��ȡͼƬ��Դ������
        List<String> list = processEngine.getRepositoryService()
                        .getDeploymentResourceNames(deploymentId);
        //����ͼƬ��Դ������
        String resourceName = "";
        if(list != null && list.size() >0) {
            for (String name : list) {
                if(name.indexOf(".png")>=0) {
                    resourceName = name ;
                }
            }
        }
        //��ȡͼƬ��������
        InputStream in = processEngine.getRepositoryService()
                        .getResourceAsStream(deploymentId, resourceName);
        
        //��ͼƬ���ɵ�D�̵�Ŀ¼��
        File file =new File("D:/"+resourceName);
        
        //����������ͼƬд��D����
        FileUtils.copyInputStreamToFile(in, file);
        
    }
    
    /**���ӹ��ܣ���ѯ���°汾�����̶���*/
    @Test
    public void findLastVersionProcessDefinition() {
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                        .createProcessDefinitionQuery()
                        .orderByProcessDefinitionVersion().asc()
                        .list();
        
        Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
        if(list != null && list.size() >0) {
            for (ProcessDefinition processDefinition : list) {
                map.put(processDefinition.getKey(), processDefinition);
            }
        }
        List<ProcessDefinition> pdList = new ArrayList<ProcessDefinition>(map.values());
        if(pdList != null && pdList.size() >0) {
            for (ProcessDefinition processDefinition : pdList) {
                System.out.println("���̶���ID:"+processDefinition.getId());
                System.out.println("���̶��������:"+processDefinition.getName());
                System.out.println("���̶����key:"+processDefinition.getKey());
                System.out.println("���̶���İ汾:"+processDefinition.getVersion());
                System.out.println("��Դ����bpmn�ļ�:"+processDefinition.getResourceName());
                System.out.println("��Դ����png�ļ�:"+processDefinition.getDiagramResourceName());
                System.out.println("�������ID:"+processDefinition.getDeploymentId());
                System.out.println("##############################################");
            }
        }
    }
    
    /**���ӹ��ܣ�ɾ�����̶��壨ɾ��key��ͬ�����в�ͬ�汾�����̶��壩*/
    @Test
    public void deleteProcessDefinition_key() {
        //���̶����key
        String processDefinitionKey = "helloworld";
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                        .createProcessDefinitionQuery()
                        .processDefinitionKey(processDefinitionKey)//ʹ�����̶����key��ѯ���൱��where����
                        .list();
        
        if(list != null && list.size() >0) {
            for (ProcessDefinition processDefinition : list) {
                String deploymentId = processDefinition.getDeploymentId();
                processEngine.getRepositoryService()
                                .deleteDeployment(deploymentId,true);
            }
            
        }
    }
    
    
    
}
