package com.esport.soap;

import com.esport.model.Player;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@WebService(name = "PlayerService")
public class PlayerServiceSOAP {
    
    // Stockage en mémoire (simulé)
    private static Map<Integer, Player> players = new ConcurrentHashMap<>();
    private static int nextId = 1;
    
    static {
        // Données initiales
        players.put(1, new Player(1, "FireKing", "fireking@esport.com", "Diamond"));
        players.put(2, new Player(2, "DarkNinja", "ninja@esport.com", "Diamond"));
        players.put(3, new Player(3, "BlazeMaster", "blaze@esport.com", "Platinum"));
        nextId = 4;
    }
    
    @WebMethod(operationName = "createPlayer")
    public Player createPlayer(
            @WebParam(name = "pseudo") String pseudo,
            @WebParam(name = "email") String email,
            @WebParam(name = "rank") String rank) {
        
        Player player = new Player(nextId++, pseudo, email, rank);
        players.put(player.getId(), player);
        System.out.println("✅ Joueur créé: " + player);
        return player;
    }
    
    @WebMethod(operationName = "getPlayer")
    public Player getPlayer(@WebParam(name = "id") int id) {
        Player player = players.get(id);
        if (player == null) {
            System.out.println("❌ Joueur non trouvé: " + id);
        }
        return player;
    }
    
    @WebMethod(operationName = "getAllPlayers")
    public List<Player> getAllPlayers() {
        return new ArrayList<>(players.values());
    }
    
    @WebMethod(operationName = "updatePlayerScore")
    public boolean updatePlayerScore(
            @WebParam(name = "id") int id,
            @WebParam(name = "score") int score) {
        
        Player player = players.get(id);
        if (player != null) {
            player.setScore(player.getScore() + score);
            System.out.println("✅ Score mis à jour pour: " + player.getPseudo());
            return true;
        }
        return false;
    }
    
    @WebMethod(operationName = "deletePlayer")
    public boolean deletePlayer(@WebParam(name = "id") int id) {
        Player removed = players.remove(id);
        if (removed != null) {
            System.out.println("✅ Joueur supprimé: " + removed.getPseudo());
            return true;
        }
        return false;
    }
    
    @WebMethod(operationName = "searchPlayerByPseudo")
    public List<Player> searchPlayerByPseudo(@WebParam(name = "pseudo") String pseudo) {
        List<Player> result = new ArrayList<>();
        for (Player player : players.values()) {
            if (player.getPseudo().toLowerCase().contains(pseudo.toLowerCase())) {
                result.add(player);
            }
        }
        return result;
    }
    
    @WebMethod(operationName = "getTopPlayers")
    public List<Player> getTopPlayers(@WebParam(name = "limit") int limit) {
        List<Player> sorted = new ArrayList<>(players.values());
        sorted.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        return sorted.subList(0, Math.min(limit, sorted.size()));
    }
}