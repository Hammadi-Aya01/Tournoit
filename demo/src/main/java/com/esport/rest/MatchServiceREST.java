package com.esport.rest;

import com.esport.model.Match;
import com.esport.model.MatchStatus;
import jakarta.persistence.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.time.LocalDateTime;
import java.util.List;

@Path("/matches")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MatchServiceREST {
    
    @PersistenceContext
    private EntityManager em;
    
    // CREATE - Créer un nouveau match
    @POST
    public Response createMatch(Match match) {
        try {
            em.getTransaction().begin();
            em.persist(match);
            em.getTransaction().commit();
            
            System.out.println("✅ Match créé: " + match.getId());
            return Response.status(Response.Status.CREATED)
                    .entity(match)
                    .build();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur: " + e.getMessage())
                    .build();
        }
    }
    
    // READ - Obtenir tous les matchs
    @GET
    public Response getAllMatches() {
        try {
            List<Match> matches = em.createQuery("SELECT m FROM Match m", Match.class)
                    .getResultList();
            return Response.ok(matches).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur: " + e.getMessage())
                    .build();
        }
    }
    
    // READ - Obtenir un match par ID
    @GET
    @Path("/{id}")
    public Response getMatch(@PathParam("id") int id) {
        try {
            Match match = em.find(Match.class, id);
            if (match == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Match non trouvé")
                        .build();
            }
            return Response.ok(match).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur: " + e.getMessage())
                    .build();
        }
    }
    
    // UPDATE - Mettre à jour un match
    @PUT
    @Path("/{id}")
    public Response updateMatch(@PathParam("id") int id, Match updatedMatch) {
        try {
            em.getTransaction().begin();
            Match match = em.find(Match.class, id);
            
            if (match == null) {
                em.getTransaction().rollback();
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Match non trouvé")
                        .build();
            }
            
            match.setScoreTeam1(updatedMatch.getScoreTeam1());
            match.setScoreTeam2(updatedMatch.getScoreTeam2());
            match.setStatus(updatedMatch.getStatus());
            match.setMatchDate(updatedMatch.getMatchDate());
            
            em.merge(match);
            em.getTransaction().commit();
            
            System.out.println("✅ Match mis à jour: " + id);
            return Response.ok(match).build();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur: " + e.getMessage())
                    .build();
        }
    }
    
    // UPDATE - Mettre à jour le score d'un match
    @PATCH
    @Path("/{id}/score")
    public Response updateScore(
            @PathParam("id") int id,
            @QueryParam("team1") int scoreTeam1,
            @QueryParam("team2") int scoreTeam2) {
        
        try {
            em.getTransaction().begin();
            Match match = em.find(Match.class, id);
            
            if (match == null) {
                em.getTransaction().rollback();
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            
            match.setScoreTeam1(scoreTeam1);
            match.setScoreTeam2(scoreTeam2);
            match.setStatus(MatchStatus.COMPLETED);
            
            em.merge(match);
            em.getTransaction().commit();
            
            System.out.println("✅ Score mis à jour: " + scoreTeam1 + "-" + scoreTeam2);
            return Response.ok(match).build();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // DELETE - Supprimer un match
    @DELETE
    @Path("/{id}")
    public Response deleteMatch(@PathParam("id") int id) {
        try {
            em.getTransaction().begin();
            Match match = em.find(Match.class, id);
            
            if (match == null) {
                em.getTransaction().rollback();
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Match non trouvé")
                        .build();
            }
            
            em.remove(match);
            em.getTransaction().commit();
            
            System.out.println("✅ Match supprimé: " + id);
            return Response.ok("Match supprimé avec succès").build();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur: " + e.getMessage())
                    .build();
        }
    }
    
    // Obtenir les matchs par statut
    @GET
    @Path("/status/{status}")
    public Response getMatchesByStatus(@PathParam("status") String status) {
        try {
            List<Match> matches = em.createQuery(
                    "SELECT m FROM Match m WHERE m.status = :status", Match.class)
                    .setParameter("status", MatchStatus.valueOf(status.toUpperCase()))
                    .getResultList();
            return Response.ok(matches).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Statut invalide")
                    .build();
        }
    }
}