package com.cmb.bankcheck.util;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-08
 * Time:15:31
 */
public class TaskUtil {
    /**
     * 通过任务名称截取部门名称
     * @param taskName
     * @return
     */
    public static String getApartNameFromTask(String taskName){
        String apart = taskName;
        if (taskName.startsWith("一级分行") || taskName.startsWith("二级分行")){
            // 先将表示分行机构的去除
            apart = taskName.substring(4, taskName.length());
        }
        if (taskName.endsWith("审批")){
            apart = taskName.substring(0, apart.length()-2);
        }

        return apart;
    }

    public static String getTaskTypeFromName(String taskName){
        if (taskName.equals("管理委员会")){
            return "sign";
        }
        return "personal";
    }

    public static String getPosition(String taskName){
        if (taskName.endsWith("行长")){
            return "行长";
        }
        if (taskName.endsWith("主任")){
            return "主管";
        }
        if (taskName.endsWith("专员")){
            return "审批专员";
        }
        else {
            return "审批人";
        }
    }
}
