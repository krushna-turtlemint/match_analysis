DROP TABLE Match IF EXISTS;
CREATE TABLE Match  (
    ID BIGINT IDENTITY NOT NULL PRIMARY KEY,
    City VARCHAR(50),
    date1  Date,
    Season VARCHAR(50),
    MatchNumber VARCHAR(50),
    Team1 VARCHAR(50),
    Team2 VARCHAR(50),
    Venue VARCHAR(50),
    TossWinner VARCHAR(50),
    TossDecision VARCHAR(50),
    SuperOver VARCHAR(50),
    WinningTeam VARCHAR(50),
    WonBy VARCHAR(50),
    Margin VARCHAR(50),
    method VARCHAR(50),
    PlayerOfMatch VARCHAR(50),
    Team1Players VARCHAR(50),
    Team2Players VARCHAR(50),
    Umpire1 VARCHAR(50),
    Umpire2 VARCHAR(50),
);
SELECT COUNT(*) FROM Match;
