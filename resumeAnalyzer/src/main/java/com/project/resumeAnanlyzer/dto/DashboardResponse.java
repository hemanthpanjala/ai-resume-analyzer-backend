package com.project.resumeAnanlyzer.dto;

import java.util.List;

public class DashboardResponse {
    private long totalJobRoles;
    private long totalAnalyses;
    private int avgMatchScore;
    private List<AnalysisSummaryResponse> recentAnalyses;

    public DashboardResponse(long totalJobRoles, long totalAnalyses, int avgMatchScore, List<AnalysisSummaryResponse> recentAnalyses) {
        this.totalJobRoles = totalJobRoles;
        this.totalAnalyses = totalAnalyses;
        this.avgMatchScore = avgMatchScore;
        this.recentAnalyses = recentAnalyses;
    }

    public long getTotalJobRoles() { return totalJobRoles; }
    public long getTotalAnalyses() { return totalAnalyses; }
    public int getAvgMatchScore() { return avgMatchScore; }
    public List<AnalysisSummaryResponse> getRecentAnalyses() { return recentAnalyses; }
}
