package com.projetoaps.SistemaHotelaria.domain.Task;

public enum TaskType {
    MAINTENANCE("maintenance"),
    CLEANING("cleaning");

    private String taskType;

    TaskType(String taskType){
        this.taskType = taskType;
    }

    public String getTaskType(){
        return taskType;
    }
}
