package models;

import java.util.List;

public class Serie {


  // Atributos de la película
      private String name;
      private String overview;
      private String first_air_date;
      private String poster_path;
      private String id;
      private List<Genero> genres;
      private String popularity;
      private String vote_average;
      private String number_of_seasons;
      private String guest_stars;
      private String number_of_episodes;


      public String getId() {
          return id;
      }

      public void setId(String id) {
          this.id = id;
      }

      /**
       * Obtener el resumen de la película
       * 
       * @return
       */
      public String getOverview() {
          return overview;
      }

      /**
       * Obtener la ruta del póster de la película
       * 
       * @return
       */
      public String getPoster_path() {
          return poster_path;
      }


      /**
       * Obtener el título de la película
       * 
       * @return
       */
      public String getName() {
          return name;
      }

      public void setTitle(String title) {
          this.name = title;
      }

      public void setOverview(String overview) {
          this.overview = overview;
      }

      public void setPoster_path(String poster_path) {
          this.poster_path = poster_path;
      }



      public List<Genero> getGenres() {
        return genres;
    }

    public void setGenres(List<Genero> genres) {
        this.genres = genres;
    }


      public String getPopularity() {
        return popularity;
      }

      public void setPopularity(String popularity) {
        this.popularity = popularity;
      }

      public String getVote_average() {
        return vote_average;
      }

      public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
      }


      public String getGuest_stars() {
        return guest_stars;
      }

      public void setGuest_stars(String guest_stars) {
        this.guest_stars = guest_stars;
      }

      public void setName(String name) {
        this.name = name;
      }

      public String getFirst_air_date() {
        return first_air_date;
      }

      public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
      }

      public String getNumber_of_seasons() {
        return number_of_seasons;
      }

      public void setNumber_of_seasons(String number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
      }

      public String getNumber_of_episodes() {
        return number_of_episodes;
      }

      public void setNumber_of_episodes(String number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
      }


      
      
  }
