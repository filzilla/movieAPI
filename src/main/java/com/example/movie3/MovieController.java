package com.example.movie3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by phillipdelia on 9/18/17.
 */
@Controller

public class MovieController {

    private String url = "http://api.themoviedb.org/3/movie/now_playing/?api_key=be2a38521a7859c95e2d73c48786e4bb";


        @RequestMapping("/")
        public String home(Model model){

        return "home";}


        @RequestMapping("/now-playing")
        public String nowPlaying (Model model){

            model.addAttribute("movies", getMovies(url));

        return "now-playing";
    }

         @RequestMapping("/medium-popular-long-name")
         public String mediumPopular(Model model){

             List<Movie> movies = getMovies(url)
                .stream()
                     .filter(movie -> movie.getTitle().length() >= 10)
                     .filter(movie -> movie.getPopularity() >= 30 && movie.getPopularity() <= 80)
                     .collect(Collectors.toList());
             
             model.addAttribute( "movies", movies);

            return "medium-popular-long-name";
    }

        public static List<Movie> getMovies(String route) {

        RestTemplate restTemplate = new RestTemplate();
        ResultsPage results = restTemplate.getForObject(route, ResultsPage.class);
        System.out.println(results);
        return results.getResults();
    }



    }