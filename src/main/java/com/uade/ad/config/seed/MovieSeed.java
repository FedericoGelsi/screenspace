package com.uade.ad.config.seed;

import com.uade.ad.model.Genre;
import com.uade.ad.model.Movie;
import com.uade.ad.repository.GenreRepository;
import com.uade.ad.repository.MovieRepository;
import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieSeed implements CommandLineRunner {
    private final MovieRepository movieRepository;

    private final GenreRepository genreRepository;

    @Override
    public void run(String... args) throws Exception {
        if(genreRepository.findAll().isEmpty()) {
            Genre cienciaFiccion = Genre.builder().genre("Ciencia Ficcion").build();
            Genre drama = Genre.builder().genre("Drama").build();
            Genre policial = Genre.builder().genre("Policial").build();
            Genre historico = Genre.builder().genre("Historico").build();
            Genre comedia = Genre.builder().genre("Comedia").build();
            Genre romance = Genre.builder().genre("Romance").build();
            Genre horror = Genre.builder().genre("Horror").build();
            Genre fantasia = Genre.builder().genre("Fantasia").build();
            Genre crimen = Genre.builder().genre("Crimen").build();
            Genre accion = Genre.builder().genre("Accion").build();

            List<Genre> genres = List.of(cienciaFiccion, drama, policial, historico, comedia, romance,
                    horror, fantasia, crimen, accion);

            genreRepository.saveAll(genres);

            genres = genreRepository.findAll();

            if (movieRepository.findAll().isEmpty()) {
                Movie cadenaPerpetua = Movie.builder()
                        .title("Cadena Perpetua")
                        .duration(142)
                        .imageUrl("https://pics.filmaffinity.com/the_shawshank_redemption-576140557-large.jpg")
                        .genres(Set.of(genres.get(1), genres.get(4)))
                        .synopsis("Andy Dufresne es encarcelado por matar a su esposa y al amante de esta. Tras una dura adaptación, intenta mejorar las condiciones de la prisión y dar esperanza a sus compañeros.")
                        .rating(4.8)
                        .isShowing(true)
                        .releaseDate(new Date(1994, 8, 30)).build();

                Movie elPadrino = Movie.builder()
                        .title("El Padrino")
                        .duration(175)
                        .imageUrl("https://cdn.sincroguia.tv/uploads/programs/e/l/-/el-padrino-2981_SPA-85.jpg")
                        .genres(Set.of(genres.get(9), genres.get(1)))
                        .synopsis("El envejecido patriarca de una dinastía del crimen organizado en la ciudad de Nueva York de la posguerra transfiere el control de su imperio clandestino a su reacio hijo menor.")
                        .rating(4.7)
                        .isShowing(true)
                        .releaseDate(new Date(1972, 5,25))
                        .build();

                Movie elCaballeroOscuro = Movie.builder()
                        .title("El Caballero Oscuro")
                        .duration(152)
                        .imageUrl("https://pics.filmaffinity.com/the_dark_knight-102763119-mmed.jpg")
                        .genres(Set.of(genres.get(1), genres.get(8), genres.get(9)))
                        .synopsis("Cuando la amenaza conocida como el Joker causa estragos y el caos en Gotham City, Batman debe aceptar una de las mayores pruebas psicológicas y físicas para luchar contra la injusticia.")
                        .rating(4.7)
                        .isShowing(true)
                        .releaseDate(new Date(2008, 6,12))
                        .build();

                Movie elPadrino2 = Movie.builder()
                        .title("El padrino (parte II)")
                        .duration(202)
                        .imageUrl("https://pics.filmaffinity.com/El_padrino_Parte_II-617194725-large.jpg")
                        .genres(Set.of(genres.get(1), genres.get(8)))
                        .synopsis("Se retratan los inicios de la vida y la carrera de Vito Corleone en el Nueva York de los años 20, mientras su hijo, Michael, amplía y refuerza su control sobre el sindicato del crimen familiar.")
                        .rating(4.5)
                        .isShowing(true)
                        .releaseDate(new Date(1974, 9,15))
                        .build();

                Movie doceHombresSinPiedad = Movie.builder()
                        .title("12 hombres sin piedad")
                        .duration(96)
                        .imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/12_Angry_Men_%281957_film_poster%29.jpg/270px-12_Angry_Men_%281957_film_poster%29.jpg")
                        .genres(Set.of(genres.get(1), genres.get(8)))
                        .synopsis("Un miembro del jurado trata de evitar un error judicial obligando al resto del jurado a reconsiderar las pruebas.")
                        .rating(4.5)
                        .isShowing(true)
                        .releaseDate(new Date(1957, 4,10))
                        .build();

                Movie laListaDeSchindler = Movie.builder()
                        .title("La lista de Schindler")
                        .duration(195)
                        .imageUrl("https://upload.wikimedia.org/wikipedia/en/3/38/Schindler%27s_List_movie.jpg")
                        .genres(Set.of(genres.get(1), genres.get(3)))
                        .synopsis("En la Polonia ocupada por los alemanes durante la Segunda Guerra Mundial, el industrial Oskar Schindler se preocupa por sus trabajadores judíos tras presenciar su persecución por los nazis.")
                        .rating(4.4)
                        .isShowing(true)
                        .releaseDate(new Date(1993, 11,30))
                        .build();

                Movie elSenorDeLosAnillosElRetornoDelRey = Movie.builder()
                        .title("El señor de los anillos: El retorno del rey")
                        .duration(201)
                        .imageUrl("https://upload.wikimedia.org/wikipedia/en/thumb/2/23/The_Lord_of_the_Rings%2C_TROTK_%282003%29.jpg/220px-The_Lord_of_the_Rings%2C_TROTK_%282003%29.jpg")
                        .genres(Set.of(genres.get(1), genres.get(7), genres.get(9)))
                        .synopsis("Gandalf y Aragorn lideran el mundo de los hombres contra la armada de Sauron para distraer su atención de Frodo y Sam, quienes se aproximan al Monte del Destino con el Anillo Único.")
                        .rating(4.3)
                        .isShowing(true)
                        .releaseDate(new Date(2003, 12,17))
                        .build();

                Movie pulpFiction = Movie.builder()
                        .title("Pulp Fiction")
                        .duration(154)
                        .imageUrl("https://upload.wikimedia.org/wikipedia/en/3/3b/Pulp_Fiction_%281994%29_poster.jpg")
                        .genres(Set.of(genres.get(1), genres.get(8), genres.get(5)))
                        .synopsis("Las vidas de dos mafiosos, un boxeador, la esposa de un gánster y un par de bandidos se entrelazan en cuatro historias de violencia y redención.")
                        .rating(4.2)
                        .isShowing(true)
                        .releaseDate(new Date(1994, 10,14))
                        .build();

                Movie elSenorDeLosAnillosLaComunidadDelAnillo = Movie.builder()
                        .title("El señor de los anillos: La comunidad del anillo")
                        .duration(178)
                        .imageUrl("https://upload.wikimedia.org/wikipedia/en/thumb/8/8a/The_Lord_of_the_Rings%2C_TFOTR_%282001%29.jpg/220px-The_Lord_of_the_Rings%2C_TFOTR_%282001%29.jpg")
                        .genres(Set.of(genres.get(1), genres.get(7), genres.get(9)))
                        .synopsis("Un hobbit de la Comarca y ocho compañeros emprenden un viaje para destruir el poderoso Anillo Único y salvar la Tierra Media del Señor Oscuro Sauron.")
                        .rating(4.1)
                        .isShowing(true)
                        .releaseDate(new Date(2001, 12,10))
                        .build();

                Movie elBuenoElFeoYElMalo = Movie.builder()
                        .title("El bueno, el feo y el malo")
                        .duration(182)
                        .imageUrl("https://upload.wikimedia.org/wikipedia/en/thumb/4/45/Good_the_bad_and_the_ugly_poster.jpg/220px-Good_the_bad_and_the_ugly_poster.jpg")
                        .genres(Set.of(genres.get(2), genres.get(6)))
                        .synopsis("Durante la Guerra de Secesión, tres cazarrecompensas se lanzan a la búsqueda de un tesoro que ninguno puede localizar sin la ayuda de los otros dos.")
                        .rating(4.0)
                        .isShowing(true)
                        .releaseDate(new Date(1966, 12,23))
                        .build();

                List<Movie> movies = List.of(cadenaPerpetua, elPadrino, elCaballeroOscuro, elPadrino2,
                        doceHombresSinPiedad, laListaDeSchindler, elSenorDeLosAnillosElRetornoDelRey, pulpFiction,
                        elSenorDeLosAnillosLaComunidadDelAnillo, elBuenoElFeoYElMalo);

                movieRepository.saveAll(movies);
            }
        }
    }
}
