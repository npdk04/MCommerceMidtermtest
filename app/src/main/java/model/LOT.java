package model;

public class LOT {
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public String getTaskTitle() {
        return TaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        TaskTitle = taskTitle;
    }

    public String getDateAssigned() {
        return DateAssigned;
    }

    public void setDateAssigned(String dateAssigned) {
        DateAssigned = dateAssigned;
    }

    public int getIsCompleted() {
        return IsCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        IsCompleted = isCompleted;
    }

    private int ID;
    private int AccountId;
    private String TaskTitle;
    private String DateAssigned;
    private int IsCompleted; // 0 = chưa hoàn thành, 1 = đã hoàn thành

    public LOT() {
    }

    public LOT(int ID, int accountId, String taskTitle, String dateAssigned, int isCompleted) {
        this.ID = ID;
        AccountId = accountId;
        TaskTitle = taskTitle;
        DateAssigned = dateAssigned;
        IsCompleted = isCompleted;
    }
}
