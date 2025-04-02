package com.szl.fed_platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szl.fed_platform.common.Result;
import com.szl.fed_platform.entity.Task;
import com.szl.fed_platform.mapper.TaskMapper;
import com.szl.fed_platform.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    TaskMapper taskMapper;

    DatasetServiceImpl datasetService;


    public TaskServiceImpl(TaskMapper taskMapper, DatasetServiceImpl datasetService) {
        this.taskMapper = taskMapper;
        this.datasetService = datasetService;
    }

    // 查看任务列表
    @Transactional
    public Result<List<Task>> findByUserId(Integer userId) {
        // 构造查询条件
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId); // 根据 user_id 查询

        // 执行查询
        List<Task> tasks = taskMapper.selectList(queryWrapper);

        // 返回结果
        return Result.success(tasks); // Result 是假设的返回封装类
    }

    // 获取结果文件
    public Result<?> getFileContent(String filePath) throws IOException {
        // 读取文件内容
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return Result.error("结果文件不存在。");
        }
        return Result.success(Files.readString(path, StandardCharsets.UTF_8));
    }

    // 创建并执行任务
    @Transactional
    public Result<?> create(Task task) throws Exception {

        try {
            task.setStartedAt(new Timestamp(System.currentTimeMillis()));

            String taskUUId = UUID.randomUUID().toString();
            task.setTaskUuid(taskUUId);

            int row = 0;
            try {
                row = taskMapper.insert(task);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            System.out.println(row);

            if (row > 0) {
                excuse(task);
                return Result.success();
            } else {
                return Result.error("任务执行失败，请检查参数。");
            }
        } catch (Exception e) {
            log.error("Unexpected error: {}");
            return Result.error("任务执行失败，请检查参数。");
        }
    }

   void excuse(Task task) {

        String python_path = "C:\\Users\\szl00\\anaconda3\\envs\\biye\\python.exe";
        String workingDirectory = "C:\\Users\\szl00\\workspace\\code\\fed_twins"; // 脚本需要运行的目录
        String pythonScriptPath = String.format("%s/main.py", workingDirectory);
        String dataset_name = datasetService.getNameById(task.getDatasetId());
        String taskId = task.getTaskUuid();
        String outputPath = workingDirectory + File.separator + "tasks" + File.separator + task.getUserId();
        String outputFilePath = outputPath + File.separator + taskId + ".txt";
        task.setResultPath(outputFilePath);
        taskMapper.updateById(task);

        File directory = new File(outputPath);
        if (!directory.exists()) {
            // 如果路径不存在，则创建目录
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create directory: " + outputPath);
            }
        }

        new Thread(() -> {
            try {
                // 构造命令
                String command = String.format("%s -u %s --dataset %s --algorithm %s  %s > %s 2>&1",
                        python_path, pythonScriptPath, dataset_name, task.getAlgorithm(), task.getParams(), outputFilePath);

                // 使用 ProcessBuilder 启动脚本
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("cmd", "/c", command); // Linux 使用 bash，Windows 使用 cmd
                processBuilder.directory(new File(workingDirectory)); // 设置工作目录
                Process process = processBuilder.start();


                // 等待脚本完成
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    // 脚本成功完成，更新任务状态为 completed
                    task.setStatus("completed");
                    task.setCompletedAt(new Timestamp(System.currentTimeMillis()));
                    task.setResultPath("/path/to/result/" + taskId); // 示例结果路径
                } else {
                    // 脚本失败，更新任务状态为 failed
                    task.setStatus("failed");
                    task.setErrorLog("脚本执行失败");
                }
            } catch (Exception e) {
                // 异常处理
                task.setStatus("failed");
                task.setErrorLog(e.getMessage());
            } finally {
                // 更新任务状态
                taskMapper.updateById(task);
            }
        }).start();
    }
}
