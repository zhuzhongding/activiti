package cn.itcast.l_group02;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

@SuppressWarnings("serial")
public class TaskListenerImpl implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        // TODO Auto-generated method stub
//        delegateTask.setAssignee("���ʦ̫");
        delegateTask.addCandidateUser("����");
        delegateTask.addCandidateUser("����");
    }

}
