package cryodex.modules.etc;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import cryodex.CryodexController.Modules;
import cryodex.Player;
import cryodex.modules.ExportController;
import cryodex.modules.Match;
import cryodex.modules.RoundPanel;
import cryodex.modules.Tournament;
import cryodex.modules.TournamentComparator;
import cryodex.modules.etc.export.EtcExportController;
import cryodex.modules.etc.gui.EtcRankingTable;
import cryodex.modules.etc.gui.EtcRoundPanel;
import cryodex.xml.XMLObject;
import cryodex.xml.XMLUtils;
import cryodex.xml.XMLUtils.Element;

public class EtcTournament extends Tournament implements XMLObject {

	private EtcExportController exportController;
	private Integer playerCount = 6;
	
	public EtcTournament(Element tournamentElement) {
		super();
		setupTournamentGUI(new EtcRankingTable(this));
		playerCount = tournamentElement.getIntegerFromChild("PLAYERCOUNT");
		if(playerCount == null){
			playerCount = 6;
		}
		loadXML(tournamentElement);
		
        Element roundElement = tournamentElement.getChild("ROUNDS");
        for (Element e : roundElement.getChildren()) {
            getAllRounds().add(new EtcRound(e, this));

        }
	}

	public EtcTournament(String name, List<Player> players, InitialSeedingEnum seedingEnum, List<Integer> points,
			boolean isSingleElimination) {
		super(name, players, seedingEnum, points, isSingleElimination);
		setupTournamentGUI(new EtcRankingTable(this));
	}

	@Override
	public Icon getIcon() {
		URL imgURL = EtcTournament.class.getResource("x.png");
		if (imgURL == null) {
			System.out.println("Failed to retrieve ETC Icon");
		}
		ImageIcon icon = new ImageIcon(imgURL);
		return icon;
	}

	@Override
	public String getModuleName() {
		return Modules.ETC.getName();
	}

	@Override
	public RoundPanel getRoundPanel(List<Match> matches) {
		return new EtcRoundPanel(this, matches);
	}

	public EtcPlayer getEtcPlayer(Player p) {
		return (EtcPlayer) p.getModuleInfoByModule(getModule());
	}

	public void massDropPlayers(int minScore, int maxCount) {

		List<Player> playerList = new ArrayList<Player>();
		playerList.addAll(getPlayers());

		Collections.sort(playerList, getRankingComparator());

		int count = 0;
		for (Player p : playerList) {
			EtcPlayer xp = getEtcPlayer(p);
			if (xp.getScore(this) < minScore || count >= maxCount) {
				getPlayers().remove(p);
			} else {
				count++;
			}
		}

		resetRankingTable();
	}

	@Override
	public List<Match> getRandomMatches(List<Player> playerList) {
		return new EtcRandomMatchGeneration(this, playerList).generateMatches();
	}

	@Override
	public TournamentComparator<Player> getRankingComparator() {
		return new EtcComparator(this, EtcComparator.rankingCompare);
	}

	@Override
	public TournamentComparator<Player> getRankingNoHeadToHeadComparator() {
		return new EtcComparator(this, EtcComparator.rankingCompareNoHeadToHead);
	}

	@Override
	public TournamentComparator<Player> getPairingComparator() {
		return new EtcComparator(this, EtcComparator.pairingCompare);
	}

	@Override
	public int getPointsDefault() {
		return 100;
	}

	@Override
	public ExportController getExportController() {
		if(exportController == null){
			exportController = new EtcExportController();
		}
		return exportController;
	}
	
	public Integer getPlayerCount(){
		return playerCount;
	}
	
	@Override
	protected List<Match> firstRoundPairings() {
        List<Match> matches = super.firstRoundPairings();
		List<Match> finalMatches = new ArrayList<Match>();
		
		for(Match im : matches){
			if(im.isBye()){
				finalMatches.add(im);
			} else {
				for(int i = 1 ; i <= getPlayerCount() ; i++){
					EtcMatch fm = EtcMatch.copyMatch(im, String.valueOf(i));
					finalMatches.add(fm);
				}
			}
		}
		
		return finalMatches; 
	}
	
	@Override
	public StringBuilder appendXML(StringBuilder sb) {
		super.appendXML(sb);
        XMLUtils.appendObject(sb, "PLAYERCOUNT", playerCount);
		return sb;
	}
}