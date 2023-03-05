package by.issoft.dto.mapper.event;

import by.issoft.domain.event.Genre;
import by.issoft.domain.event.Movie;
import by.issoft.domain.event.MovieImage;
import by.issoft.dto.in.event.MovieInDTO;
import by.issoft.dto.out.event.MovieOutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = GenreMapper.class)
public interface MovieMapper {

    @Mapping(source = "genresIds", target = "genres", qualifiedByName = "idsToGenres")
    @Mapping(source = "imagesUrls", target = "movieImages", qualifiedByName = "imagesUrlsToMovieImages")
    Movie fromDto(MovieInDTO movieInDTO);

    MovieOutDTO toDto(Movie movie);

    @Named("idsToGenres")
    static List<Genre> idsToGenres(List<Long> genresIds) {
        return genresIds.stream().map(genreId -> (Genre) Genre.builder().id(genreId).build()).toList();
    }

    @Named("imagesUrlsToMovieImages")
    static List<MovieImage> imagesUrlsToMovieImages(List<String> imagesUrls) {
        return imagesUrls.stream().map(imagesUrl -> (MovieImage) MovieImage.builder().url(imagesUrl).build()).toList();
    }
}
