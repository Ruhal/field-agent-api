package learn.field_agent.models;


import java.time.LocalDate;

public class Mission {

    private int missionId;
    private String codeName;
    private String notes;
    private LocalDate startDate;
    private LocalDate projectedEndDate;
    private LocalDate actualEndDate;
    private double operationalCost;
    private int agencyId;

    public int getMissionId(){
        return this.missionId;
    }

    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }

    public String getCodeName(){
        return  this.codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getProjectedEndDate() {
        return this.projectedEndDate;
    }

    public void setProjectedEndDate(LocalDate projectedEndDate) {
        this.projectedEndDate = projectedEndDate;
    }

    public LocalDate getActualEndDate() {
        return this.actualEndDate;
    }

    public void setActualEndDate(LocalDate actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public double getOperationalCost() {
        return operationalCost;
    }

    public void setOperationalCost(double operationalCost) {
        this.operationalCost = operationalCost;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }
}
