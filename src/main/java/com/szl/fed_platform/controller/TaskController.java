package com.szl.fed_platform.controller;

import com.szl.fed_platform.common.Result;
import com.szl.fed_platform.entity.Task;
import com.szl.fed_platform.service.impl.TaskServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    TaskServiceImpl taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @PostMapping("create")
    public Result<?> create(@RequestBody Task task) throws Exception {
        taskService.create(task);
        return Result.success("任务已创建");
    }

    @GetMapping("findByUserId")
    public Result<List<Task>> findByUserId(@RequestParam Integer userId) {
        return taskService.findByUserId(userId);
    }

    @GetMapping("getResult")
    public Result<?> getResult(@RequestParam String resultPath) throws IOException {
        return taskService.getFileContent(resultPath);
    }
}
