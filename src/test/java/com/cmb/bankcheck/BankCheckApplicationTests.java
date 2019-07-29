package com.cmb.bankcheck;

import com.cmb.bankcheck.config.AppConfig;
import com.cmb.bankcheck.message.QueryUserMessage;
import com.cmb.bankcheck.service.DeployService;
import com.cmb.bankcheck.service.ProcessService;
import com.cmb.bankcheck.service.UserManagerService;
import org.activiti.engine.*;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankCheckApplicationTests {

    @Test
    public void contextLoads() {
    }

//    public void testPost(){
//        RestTemplate client = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        HttpMethod method = HttpMethod.POST;
//        // 以表单的方式提交
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        //将请求头部和参数合成一个请求
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("name","张三");
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
//        String url = "http://localhost:8888/query_by_name";
//        ResponseEntity<QueryUserMessage> response = client.postForEntity(url, requestEntity, QueryUserMessage.class);
//        QueryUserMessage msg = response.getBody();
//        List<User> users = msg.getData();
//        for(User user:users){
//            System.out.println(user.getName()+" "+user.getAge());
//        }
//        System.out.println(msg.getMsg());
//    }
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ProcessEngine processEngine;
    @Test
    public void testDeploy(){

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.processDefinitionName("银行流程审批");
        processDefinitionQuery.listPage(0,1);
        processDefinitionQuery.orderByProcessDefinitionVersion().desc();
        String key = processDefinitionQuery.list().get(0).getKey();
        System.out.println(key);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        System.out.println(processInstance.getId());

    }

    @Autowired
    UserManagerService userManagerService;

    @Test
    public void testUserGroup(){
        List<String> userIds = new ArrayList<>();
        userIds.add("李四");
        userIds.add("王五");
        userIds.add("大佬");
        userIds.add("巨佬");
        userManagerService.createUsers(userIds);
        userManagerService.createGroup("业务管理委员会");
        userManagerService.addUsersToGroup(userIds, "业务管理委员会");
    }

    @Test
    public void testDoTask(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1");
        System.out.println(processInstance.getId());
        TaskQuery query = taskService.createTaskQuery();

        query.processInstanceId(processInstance.getId());
        List<Task> list = query.list();
        String taskId = list.get(0).getId();
        taskService.complete(taskId);

    }

    @Autowired
    IdentityService identityService;
    @Test
    public void testQueryHandler(){
        TaskQuery taskQuery = taskService.createTaskQuery();
        //taskQuery.taskCandidateGroup("业务管理委员会");
        UserQuery userQuery = identityService.createUserQuery();
        userQuery.memberOfGroup("业务管理委员会");
        List<User> userList = userQuery.list();
        for (User user:userList){
            System.out.println(user.getId());
        }
//        List<Task> list = taskQuery.list();
//        for (Task task:list){
//            //taskService.setAssignee(task.getId(), userList.get(0).getId());
//            System.out.println(task.getName()+" "+task.getId()+" "+task.getAssignee());
       // }
        taskQuery.taskAssignee("大佬");
        List<Task> list = taskQuery.list();
        for (Task task:list){
            //taskService.setAssignee(task.getId(), userList.get(0).getId());
            System.out.println(task.getName()+" "+task.getId()+" "+task.getAssignee());
        }

    }

    @Autowired
    AppConfig config;
    @Autowired
    ProcessService service;
    @Test
    public void testProcess(){
        String key = "bankMerge1";
        service.startProcessByKey("");


    }
}
