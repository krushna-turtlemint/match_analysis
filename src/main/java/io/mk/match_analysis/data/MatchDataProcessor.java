package io.mk.match_analysis.data;

import org.springframework.batch.item.ItemProcessor;

import io.mk.match_analysis.model.Match;

public class MatchDataProcessor implements ItemProcessor<matchInput, Match> {

    // private static final Logger log =
    // LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public Match process(final matchInput matchInput) throws Exception {
        Match match = new Match();
        match.setID(matchInput.getID());
        match.setCity(matchInput.getCity());
        match.setSeason(matchInput.getSeason());
        match.setDate(matchInput.getDate());
        match.setPlayerOfMatch(matchInput.getPlayer_of_Match());

        String FirstInningTeam, secondInningTeam;

        if ("bat".equals(matchInput.getTossDecision())) {
            FirstInningTeam = matchInput.getTossDecision();
            secondInningTeam = FirstInningTeam.equals(matchInput.getTeam1()) ? matchInput.getTeam2()
                    : matchInput.getTeam1();
        } else {
            secondInningTeam = matchInput.getTossDecision();
            FirstInningTeam = secondInningTeam.equals(matchInput.getTeam1()) ? matchInput.getTeam2()
                    : matchInput.getTeam1();
        }

        match.setTeam1(FirstInningTeam);
        match.setTeam2(secondInningTeam);
        match.setTossWinner(matchInput.getTossWinner());
        match.setVenue(matchInput.getVenue());
        match.setTossDecision(matchInput.getTossDecision());
        match.setSuperOver(matchInput.getSuperOver());
        match.setWinningTeam(matchInput.getWinningTeam());
        match.setWonBy(matchInput.getWonBy());
        match.setMargin(matchInput.getMargin());
        match.setMethod(matchInput.getMethod());
        match.setPlayerOfMatch(matchInput.getPlayer_of_Match());
        if (FirstInningTeam.equals(matchInput.getTeam1())) {
            match.setTeam1Players(matchInput.getTeam1Players());
            match.setTeam2Players(matchInput.getTeam2Players());
        } else {
            match.setTeam1Players(matchInput.getTeam2Players());
            match.setTeam2Players(matchInput.getTeam1Players());
        }
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());
        return match;
    }

}
