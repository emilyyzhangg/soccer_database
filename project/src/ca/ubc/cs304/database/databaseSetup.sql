DROP TABLE League CASCADE CONSTRAINTS;
DROP TABLE Team CASCADE CONSTRAINTS;
DROP TABLE RefereeInfo CASCADE CONSTRAINTS;
DROP TABLE RefereeExperience;
DROP TABLE Coach;
DROP TABLE Position CASCADE CONSTRAINTS;
DROP TABLE PlayerInfo CASCADE CONSTRAINTS;
DROP TABLE PlayerGoals;
DROP TABLE PlayerExperience;
DROP TABLE Goalie;
DROP TABLE Defender;
DROP TABLE Midfielder;
DROP TABLE Striker;
DROP TABLE GameRefs CASCADE CONSTRAINTS;
DROP TABLE GameInfo;
DROP TABLE DBUser CASCADE CONSTRAINTS;
DROP TABLE Bet;
DROP TABLE Participates;
DROP TABLE PlaysFor;
DROP TABLE Plays;

CREATE TABLE League(leagueID integer PRIMARY KEY, leagueName varchar(20));
INSERT INTO League VALUES(1, 'Premier League');
INSERT INTO League VALUES(2, 'EFL Championship');
INSERT INTO League VALUES(3, 'Bundesliga');
INSERT INTO League VALUES(4, 'LaLiga');
INSERT INTO League VALUES(5, 'Series A');

CREATE TABLE Team(teamID integer PRIMARY KEY, leagueID integer NOT NULL REFERENCES League(leagueID) ON DELETE CASCADE, teamName varchar(50), city varchar(50), wins integer, draws integer, losses integer);
INSERT INTO Team VALUES(1, 1, 'Tottenham Hotspurs', 'London', 10, 0, 3);
INSERT INTO Team VALUES(2, 1, 'Arsenal FC', 'London', 2, 1, 10);
INSERT INTO Team VALUES(3, 1, 'Chelsea FC', 'London', 4, 6, 3);
INSERT INTO Team VALUES(4, 1, 'WestHam United', 'London', 0, 0, 13);
INSERT INTO Team VALUES(5, 2, 'Stoke City FC', 'Stoke-On-Trent', 10, 0, 3);


CREATE TABLE RefereeInfo(refereeID integer PRIMARY KEY, firstName varchar(50), lastName varchar(50));
INSERT INTO RefereeInfo VALUES(1, 'John', 'Doe');
INSERT INTO RefereeInfo VALUES(2, 'Jane', 'Doe');
INSERT INTO RefereeInfo VALUES(3, 'John', 'Smith');
INSERT INTO RefereeInfo VALUES(4, 'James', 'Hu');
INSERT INTO RefereeInfo VALUES(5, 'Jack', 'Jill');

CREATE TABLE RefereeExperience(refereeID integer NOT NULL REFERENCES RefereeInfo(refereeID) ON DELETE CASCADE, yearStarted integer, yearsOfExperience integer, CONSTRAINT RefereeExperience_pk PRIMARY KEY (refereeID, yearStarted));
INSERT INTO RefereeExperience VALUES(1, 2000, 23);
INSERT INTO RefereeExperience VALUES(2, 2010, 13);
INSERT INTO RefereeExperience VALUES(3, 2020, 3);
INSERT INTO RefereeExperience VALUES(4, 2020, 3);
INSERT INTO RefereeExperience VALUES(5, 2022, 1);

CREATE TABLE Coach(coachID integer PRIMARY KEY, teamID integer NOT NULL REFERENCES Team(teamID)  ON DELETE CASCADE, firstName varchar(50), lastName varchar(50));
INSERT INTO Coach VALUES(1, 1, 'Bob', 'Builder');
INSERT INTO Coach VALUES(2, 2, 'Bob', 'Mackey');
INSERT INTO Coach VALUES(3, 3, 'Josh', 'Schneider');
INSERT INTO Coach VALUES(4, 4, 'John', 'Lennon');
INSERT INTO Coach VALUES(5, 5, 'Paul', 'McCartney');



CREATE TABLE Position(positionID integer PRIMARY KEY);
INSERT INTO Position VALUES(1);
INSERT INTO Position VALUES(2);
INSERT INTO Position VALUES(3);
INSERT INTO Position VALUES(4);

CREATE TABLE PlayerInfo(playerID integer PRIMARY KEY, positionID integer NOT NULL REFERENCES Position(positionID), firstName varchar(50), lastName varchar(50), age integer, jerseyNumber integer, teamID integer NOT NULL REFERENCES Team(teamID) ON DELETE CASCADE);
INSERT INTO PlayerInfo VALUES(1, 1, 'George', 'Jungle', 25, 10, 2);
INSERT INTO PlayerInfo VALUES(6, 3, 'Harry', 'Styles', 25, 10, 2);
INSERT INTO PlayerInfo VALUES(7, 2, 'Ringo', 'Starr', 25, 10, 2);
INSERT INTO PlayerInfo VALUES(8, 4, 'George', 'Harrison', 25, 10, 2);

INSERT INTO PlayerInfo VALUES(4, 1, 'Julian', 'Casablancas', 32, 5, 3);
INSERT INTO PlayerInfo VALUES(15, 2, 'Nick', 'Valensi', 30, 5, 3);
INSERT INTO PlayerInfo VALUES(16, 3, 'Nikolai', 'Fraiture', 28, 5, 3);
INSERT INTO PlayerInfo VALUES(17, 4, 'Albert', 'Hammond Jr.', 30, 5, 3);

INSERT INTO PlayerInfo VALUES(5, 1, 'Fabrizio', 'Moretti', 21, 10, 4);
INSERT INTO PlayerInfo VALUES(9, 2, 'Will', 'Toledo', 24, 10, 4);
INSERT INTO PlayerInfo VALUES(10, 3, 'Andrew', 'Katz', 26, 10, 4);
INSERT INTO PlayerInfo VALUES(11, 4, 'Seth', 'Dalby', 20, 10, 4);

INSERT INTO PlayerInfo VALUES(2, 4, 'Heung-Min', 'Son', 20, 7, 1); 
INSERT INTO PlayerInfo VALUES(3, 3, 'Harry', 'Kane', 29, 10, 1);
INSERT INTO PlayerInfo VALUES(12, 1, 'Ethan', 'Ives', 24, 11, 1);
INSERT INTO PlayerInfo VALUES(13, 2, 'Emmy', 'Jack', 25, 12, 1);
INSERT INTO PlayerInfo VALUES(18, 2, 'Alex', 'Turner', 25, 1, 1);
INSERT INTO PlayerInfo VALUES(19, 2, 'Nick', 'O Malley', 25, 2, 1);
INSERT INTO PlayerInfo VALUES(20, 2, 'Jamie', 'Cooke', 25, 5, 1);
INSERT INTO PlayerInfo VALUES(21, 2, 'Matt', 'Helder', 25, 7, 1);
INSERT INTO PlayerInfo VALUES(22, 2, 'Matt', 'Damon', 25, 8, 1);
INSERT INTO PlayerInfo VALUES(14, 2, 'Will', 'Barnes', 25, 9, 1);
INSERT INTO PlayerInfo VALUES(23, 2, 'Jack', 'Black', 25, 13, 1);

CREATE TABLE PlayerGoals(playerID integer NOT NULL REFERENCES PlayerInfo(playerID) ON DELETE CASCADE, goalsScored integer, assists integer, goalAssistRatio number, CONSTRAINT PlayerGoals_pk PRIMARY KEY (playerID, goalsScored, assists));
INSERT INTO PlayerGoals VALUES(1, 10, 10, 1);
INSERT INTO PlayerGoals VALUES(2, 3, 6, 0.5);
INSERT INTO PlayerGoals VALUES(3, 3, 12, 0.25);
INSERT INTO PlayerGoals VALUES(4, 10, 5, 2);
INSERT INTO PlayerGoals VALUES(6, 5, 5, 1);
INSERT INTO PlayerGoals VALUES(19, 4, 5, 0.8);
INSERT INTO PlayerGoals VALUES(7, 3, 5, 0.6);
INSERT INTO PlayerGoals VALUES(8, 2, 5, 0.4);
INSERT INTO PlayerGoals VALUES(9, 1, 5, 0.2);
INSERT INTO PlayerGoals VALUES(10, 0, 5, 0);
INSERT INTO PlayerGoals VALUES(11, 5, 0, 0);
INSERT INTO PlayerGoals VALUES(12, 0, 6, 0);
INSERT INTO PlayerGoals VALUES(13, 5, 7, 0.71);
INSERT INTO PlayerGoals VALUES(14, 5, 6, 0.83);
INSERT INTO PlayerGoals VALUES(15, 5, 1, 5);
INSERT INTO PlayerGoals VALUES(16, 0, 5, 0);
INSERT INTO PlayerGoals VALUES(17, 5, 0, 0);
INSERT INTO PlayerGoals VALUES(18, 5, 5, 1);

CREATE TABLE PlayerExperience(playerID integer NOT NULL REFERENCES PlayerInfo(playerID) ON DELETE CASCADE, yearStarted integer, yearsOfExperience integer, CONSTRAINT PlayerExperience_pk PRIMARY KEY (playerID, yearStarted));
INSERT INTO PlayerExperience VALUES(1, 2020, 3);
INSERT INTO PlayerExperience VALUES(2, 2021, 2);
INSERT INTO PlayerExperience VALUES(3, 2019, 4);
INSERT INTO PlayerExperience VALUES(4, 2022, 1);
INSERT INTO PlayerExperience VALUES(5, 2015, 8);
INSERT INTO PlayerExperience VALUES(6, 2015, 8);
INSERT INTO PlayerExperience VALUES(7, 2015, 8);
INSERT INTO PlayerExperience VALUES(8, 2014, 9);
INSERT INTO PlayerExperience VALUES(9, 2015, 8);
INSERT INTO PlayerExperience VALUES(10, 2010, 13);
INSERT INTO PlayerExperience VALUES(11, 2015, 8);
INSERT INTO PlayerExperience VALUES(12, 2015, 8);
INSERT INTO PlayerExperience VALUES(13, 2015, 8);
INSERT INTO PlayerExperience VALUES(14, 2015, 8);
INSERT INTO PlayerExperience VALUES(15, 2015, 8);
INSERT INTO PlayerExperience VALUES(16, 2010, 13);
INSERT INTO PlayerExperience VALUES(17, 2015, 8);
INSERT INTO PlayerExperience VALUES(18, 2015, 8);
INSERT INTO PlayerExperience VALUES(19, 2015, 8);
INSERT INTO PlayerExperience VALUES(20, 2009, 14);
INSERT INTO PlayerExperience VALUES(21, 2015, 8);
INSERT INTO PlayerExperience VALUES(22, 2008, 15);
INSERT INTO PlayerExperience VALUES(23, 2015, 8);


CREATE TABLE Goalie(playerID integer PRIMARY KEY, positionID integer NOT NULL REFERENCES Position(positionID) ON DELETE CASCADE);
INSERT INTO Goalie VALUES(1, 1);
INSERT INTO Goalie VALUES(6, 1);
INSERT INTO Goalie VALUES(7, 1);
INSERT INTO Goalie VALUES(8, 1);

CREATE TABLE Defender(playerID integer PRIMARY KEY, positionID integer NOT NULL REFERENCES Position(positionID) ON DELETE CASCADE);
INSERT INTO Defender VALUES(4, 2);
INSERT INTO Defender VALUES(15, 2);
INSERT INTO Defender VALUES(16, 2);
INSERT INTO Defender VALUES(17, 2);

CREATE TABLE Midfielder(playerID integer PRIMARY KEY, positionID integer NOT NULL REFERENCES Position(positionID) ON DELETE CASCADE);
INSERT INTO Midfielder VALUES(5, 3);
INSERT INTO Midfielder VALUES(9, 3);
INSERT INTO Midfielder VALUES(10, 3);
INSERT INTO Midfielder VALUES(11, 3);

CREATE TABLE Striker(playerID integer PRIMARY KEY, positionID integer NOT NULL REFERENCES Position(positionID) ON DELETE CASCADE);
INSERT INTO Striker VALUES(2, 4);
INSERT INTO Striker VALUES(3, 4);
INSERT INTO Striker VALUES(12, 4);
INSERT INTO Striker VALUES(13, 4);

CREATE TABLE GameRefs(gameID integer PRIMARY KEY, refereeID integer NOT NULL REFERENCES RefereeInfo(refereeID) ON DELETE CASCADE);
INSERT INTO GameRefs VALUES(1, 1);
INSERT INTO GameRefs VALUES(2, 2);
INSERT INTO GameRefs VALUES(3, 3);
INSERT INTO GameRefs VALUES(4, 4);
INSERT INTO GameRefs VALUES(5, 5);

CREATE TABLE GameInfo(gameID integer NOT NULL REFERENCES GameRefs(gameID) ON DELETE CASCADE, awayTeam integer, homeTeam integer, gameDay date, gameLocation varchar(50), CONSTRAINT GameInfo_pk PRIMARY KEY (awayTeam, homeTeam, gameDay));
INSERT INTO GameInfo VALUES(1, 1, 2, TO_DATE('2023-10-01', 'yyyy/mm/dd'), 'Emirates Stadium');
INSERT INTO GameInfo VALUES(2, 2, 1, TO_DATE('2023-10-20', 'yyyy/mm/dd'), 'Tottenham Hotspur Stadium');
INSERT INTO GameInfo VALUES(3, 1, 3, TO_DATE('2023-10-21', 'yyyy/mm/dd'), 'Stamford Bridge');
INSERT INTO GameInfo VALUES(4, 4, 5, TO_DATE('2023-10-22', 'yyyy/mm/dd'), 'Bet 365 Stadium');
INSERT INTO GameInfo VALUES(5, 3, 4, TO_DATE('2023-10-10', 'yyyy/mm/dd'), 'London Stadium');

CREATE TABLE DBUser(userID integer PRIMARY KEY, accountBalance number);
INSERT INTO DBUser VALUES(1, 1000);
INSERT INTO DBUser VALUES(2, 10);
INSERT INTO DBUser VALUES(3, 100000);
INSERT INTO DBUser VALUES(4, 500);
INSERT INTO DBUser VALUES(5, 100);

CREATE TABLE Bet(userID integer NOT NULL REFERENCES DBUser(userID) ON DELETE CASCADE, gameID integer NOT NULL REFERENCES GameRefs(gameID) ON DELETE CASCADE, teamID integer, amount integer, CONSTRAINT bet_pk PRIMARY KEY (userID, gameID));
INSERT INTO Bet VALUES(1, 1, 1, 10);
INSERT INTO Bet VALUES(2, 1, 2, 50);
INSERT INTO Bet VALUES(3, 3, 1, 100);
INSERT INTO Bet VALUES(1, 4, 4, 20);
INSERT INTO Bet VALUES(1, 3, 1, 50);
INSERT INTO Bet VALUES(1, 2, 1, 110);

CREATE TABLE Participates(teamID integer NOT NULL REFERENCES Team(teamID) ON DELETE CASCADE, gameID integer NOT NULL REFERENCES GameRefs(gameID) ON DELETE CASCADE, CONSTRAINT Participates_pk PRIMARY KEY(teamID, gameID));
INSERT INTO Participates VALUES(1, 1);
INSERT INTO Participates VALUES(2, 1);
INSERT INTO Participates VALUES(2, 2);
INSERT INTO Participates VALUES(1, 2);
INSERT INTO Participates VALUES(1, 3);
INSERT INTO Participates VALUES(3, 3);
INSERT INTO Participates VALUES(4, 4);
INSERT INTO Participates VALUES(5, 4);
INSERT INTO Participates VALUES(3, 5);
INSERT INTO Participates VALUES(4, 5);


CREATE TABLE Plays(playerID integer NOT NULL REFERENCES PlayerInfo(playerID) ON DELETE CASCADE, positionID integer REFERENCES Position(positionID) ON DELETE CASCADE, CONSTRAINT Plays_pk PRIMARY KEY(playerID, positionID));
INSERT INTO Plays VALUES(1, 1);
INSERT INTO Plays VALUES(2, 4);
INSERT INTO Plays VALUES(3, 4);
INSERT INTO Plays VALUES(4, 1);
INSERT INTO Plays VALUES(5, 2);
INSERT INTO Plays VALUES(6, 2);
INSERT INTO Plays VALUES(7, 3);
INSERT INTO Plays VALUES(8, 4);
INSERT INTO Plays VALUES(9, 1);
INSERT INTO Plays VALUES(10, 2);
INSERT INTO Plays VALUES(11, 3);
INSERT INTO Plays VALUES(12, 4);
INSERT INTO Plays VALUES(13, 1);
INSERT INTO Plays VALUES(14, 4);
INSERT INTO Plays VALUES(15, 3);
INSERT INTO Plays VALUES(16, 1);
INSERT INTO Plays VALUES(17, 4);
INSERT INTO Plays VALUES(18, 4);
INSERT INTO Plays VALUES(19, 4);
INSERT INTO Plays VALUES(20, 4);
INSERT INTO Plays VALUES(21, 1);
INSERT INTO Plays VALUES(22, 2);
INSERT INTO Plays VALUES(23, 3);

CREATE TABLE PlaysFor(teamID integer NOT NULL REFERENCES Team(teamID) ON DELETE CASCADE, playerID integer REFERENCES PlayerInfo(playerID) ON DELETE CASCADE, CONSTRAINT playsfor_pk PRIMARY KEY (playerID, teamID));
INSERT INTO PlaysFor VALUES(1, 2);
INSERT INTO PlaysFor VALUES(1, 3);
INSERT INTO PlaysFor VALUES(1, 4);
INSERT INTO PlaysFor VALUES(1, 5);
INSERT INTO PlaysFor VALUES(2, 6);
INSERT INTO PlaysFor VALUES(2, 7);
INSERT INTO PlaysFor VALUES(2, 8);
INSERT INTO PlaysFor VALUES(2, 9);
INSERT INTO PlaysFor VALUES(2, 10);
INSERT INTO PlaysFor VALUES(3, 11);
INSERT INTO PlaysFor VALUES(3, 12);
INSERT INTO PlaysFor VALUES(3, 13);
INSERT INTO PlaysFor VALUES(3, 14);
INSERT INTO PlaysFor VALUES(4, 15);
INSERT INTO PlaysFor VALUES(4, 16);
INSERT INTO PlaysFor VALUES(5, 17);
INSERT INTO PlaysFor VALUES(5, 18);
INSERT INTO PlaysFor VALUES(5, 19);
INSERT INTO PlaysFor VALUES(5, 20);
INSERT INTO PlaysFor VALUES(5, 21);
INSERT INTO PlaysFor VALUES(5, 22);
INSERT INTO PlaysFor VALUES(5, 23);