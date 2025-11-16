package modules;

import java.sql.Timestamp;
import java.util.Date;

public class Match {
    private Long id;
    private String homeTeam;
    private String awayTeam;
    private String competition;
    private String score;
    private Date matchDate;
    private String stadium;
    private String previewImageUrl;
    private String homeTeamLogoUrl;
    private String awayTeamLogoUrl;

    public Match() {}

    public Match(Long id, String homeTeam, String awayTeam, String competition, String score, Timestamp timestamp, String stadium, String previewImageUrl, String homeTeamLogoUrl, String awayTeamLogoUrl) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.competition = competition;
        this.score = score;
        this.matchDate = new Date(timestamp.getTime());
        this.stadium = stadium;
        this.previewImageUrl = previewImageUrl;
        this.homeTeamLogoUrl = homeTeamLogoUrl;
        this.awayTeamLogoUrl = awayTeamLogoUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHomeTeam() { return homeTeam; }
    public void setHomeTeam(String homeTeam) { this.homeTeam = homeTeam; }

    public String getAwayTeam() { return awayTeam; }
    public void setAwayTeam(String awayTeam) { this.awayTeam = awayTeam; }

    public String getCompetition() { return competition; }
    public void setCompetition(String competition) { this.competition = competition; }

    public String getScore() { return score;}
    public void setScore(String score) { this.score = score;}

    public Date getMatchDate() {return matchDate;}
    public void setMatchDate(Date matchDate) {this.matchDate = matchDate;}

    public String getStadium() {return stadium;}
    public void setStadium(String stadium) {this.stadium = stadium;}

    public String getHomeTeamLogoUrl() {return homeTeamLogoUrl;}
    public void setHomeTeamLogoUrl(String homeTeamLogoUrl) {this.homeTeamLogoUrl = homeTeamLogoUrl;}

    public String getAwayTeamLogoUrl() {return awayTeamLogoUrl;}
    public void setAwayTeamLogoUrl(String awayTeamLogoUrl) {this.awayTeamLogoUrl = awayTeamLogoUrl;}

    public String getPreviewImageUrl() { return previewImageUrl; }
    public void setPreviewImageUrl(String previewImageUrl) { this.previewImageUrl = previewImageUrl; }
}
