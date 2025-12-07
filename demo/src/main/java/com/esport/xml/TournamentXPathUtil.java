package com.esport.xml;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.File;
import java.util.*;

/**
 * Utilitaire pour interroger le fichier XML du tournoi avec XPath
 */
public class TournamentXPathUtil {
    
    private Document document;
    private XPath xpath;
    
    public TournamentXPathUtil(String xmlFilePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false); // Désactiver les namespaces
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        this.document = builder.parse(new File(xmlFilePath));
        
        XPathFactory xpathFactory = XPathFactory.newInstance();
        this.xpath = xpathFactory.newXPath();
    }
    
    /**
     * Obtenir le nom du tournoi
     */
    public String getTournamentName() throws XPathExpressionException {
        String expression = "//Tournament/name/text()";
        return (String) xpath.evaluate(expression, document, XPathConstants.STRING);
    }
    
    /**
     * Obtenir tous les pseudos des joueurs
     */
    public List<String> getAllPlayerPseudos() throws XPathExpressionException {
        String expression = "//Player/pseudo/text()";
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        
        List<String> pseudos = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            pseudos.add(nodes.item(i).getNodeValue());
        }
        return pseudos;
    }
    
    /**
     * Obtenir le nombre total d'équipes
     */
    public int getTotalTeams() throws XPathExpressionException {
        String expression = "count(//Team)";
        Double count = (Double) xpath.evaluate(expression, document, XPathConstants.NUMBER);
        return count.intValue();
    }
    
    /**
     * Obtenir les joueurs d'une équipe par son nom
     */
    public List<String> getPlayersByTeamName(String teamName) throws XPathExpressionException {
        String expression = String.format(
            "//Team[name/text()='%s']//Player/pseudo/text()",
            teamName
        );
        
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        
        List<String> players = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            players.add(nodes.item(i).getNodeValue());
        }
        return players;
    }
    
    /**
     * Obtenir tous les matchs programmés (SCHEDULED)
     */
    public int getScheduledMatchesCount() throws XPathExpressionException {
        String expression = "count(//Match[status/text()='SCHEDULED'])";
        Double count = (Double) xpath.evaluate(expression, document, XPathConstants.NUMBER);
        return count.intValue();
    }
    
    /**
     * Obtenir les joueurs avec un rank spécifique
     */
    public List<String> getPlayersByRank(String rank) throws XPathExpressionException {
        String expression = String.format(
            "//Player[rank/text()='%s']/pseudo/text()",
            rank
        );
        
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        
        List<String> players = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            players.add(nodes.item(i).getNodeValue());
        }
        return players;
    }
    
    /**
     * Obtenir le score moyen des joueurs
     */
    public double getAveragePlayerScore() throws XPathExpressionException {
        String sumExpression = "sum(//Player/score)";
        String countExpression = "count(//Player/score)";
        
        Double sum = (Double) xpath.evaluate(sumExpression, document, XPathConstants.NUMBER);
        Double count = (Double) xpath.evaluate(countExpression, document, XPathConstants.NUMBER);
        
        return count > 0 ? sum / count : 0.0;
    }
    
    /**
     * Obtenir les détails d'un match par son ID
     */
    public Map<String, String> getMatchDetails(int matchId) throws XPathExpressionException {
        String baseExpression = String.format("//Match[id/text()='%d']", matchId);
        
        Map<String, String> details = new HashMap<>();
        details.put("team1Id", (String) xpath.evaluate(
            baseExpression + "/team1Id/text()", 
            document, XPathConstants.STRING));
        details.put("team2Id", (String) xpath.evaluate(
            baseExpression + "/team2Id/text()", 
            document, XPathConstants.STRING));
        details.put("status", (String) xpath.evaluate(
            baseExpression + "/status/text()", 
            document, XPathConstants.STRING));
        
        return details;
    }
    
    /**
     * Tester toutes les fonctionnalités XPath
     */
    public static void main(String[] args) {
        try {
            // Utiliser le chemin direct pour les tests
            String xmlPath = "src/main/resources/tournament.xml";
            TournamentXPathUtil util = new TournamentXPathUtil(xmlPath);
            
            System.out.println("=== Test XPath sur tournament.xml ===\n");
            
            System.out.println("🏆 Nom du tournoi: " + util.getTournamentName());
            System.out.println("👥 Nombre d'équipes: " + util.getTotalTeams());
            System.out.println("📅 Matchs programmés: " + util.getScheduledMatchesCount());
            System.out.println("📊 Score moyen: " + util.getAveragePlayerScore());
            
            System.out.println("\n🎮 Tous les joueurs:");
            for (String pseudo : util.getAllPlayerPseudos()) {
                System.out.println("  - " + pseudo);
            }
            
            System.out.println("\n💎 Joueurs Diamond:");
            for (String pseudo : util.getPlayersByRank("Diamond")) {
                System.out.println("  - " + pseudo);
            }
            
            System.out.println("\n🔥 Joueurs de Phoenix Squad:");
            for (String pseudo : util.getPlayersByTeamName("Phoenix Squad")) {
                System.out.println("  - " + pseudo);
            }
            
            System.out.println("\n⚔️ Détails du match #1:");
            Map<String, String> match = util.getMatchDetails(1);
            match.forEach((key, value) -> 
                System.out.println("  " + key + ": " + value));
            
            System.out.println("\n✅ Test XPath terminé avec succès!");
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du test XPath:");
            e.printStackTrace();
        }
    }
}