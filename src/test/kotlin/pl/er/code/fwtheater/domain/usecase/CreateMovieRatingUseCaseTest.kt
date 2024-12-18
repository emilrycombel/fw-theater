package pl.er.code.fwtheater.domain.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import pl.er.code.fwtheater.domain.exception.BusinessRuleException
import pl.er.code.fwtheater.domain.exception.InvalidStateException
import pl.er.code.fwtheater.domain.model.Movie
import pl.er.code.fwtheater.domain.model.MovieRating
import pl.er.code.fwtheater.domain.service.ProfanityService


class CreateMovieRatingUseCaseTest {

    private val profanityService = Mockito.mock(ProfanityService::class.java)
    private val createMovieRatingUseCase = CreateMovieRatingUseCase(profanityService)

    @Test
    fun `createMovieRating should return MovieRating with correct properties`() {
        val movie = object : Movie {
            override var id: String? = "movieId"
            override var title: String? = "movieTitle"
        }
        val rating: Short = 4
        val comment = "Great movie!"
        val ratingUser = "JohnDoe"
        val movieRatingInstance = object : MovieRating {
            override var id: String? = null
            override var rating: Short? = null
            override var comment: String? = null
            override var ratingUser: String? = null
            override var movie: Movie? = null
        }

        val instanceSupplier = { movieRatingInstance }

        val result = createMovieRatingUseCase.createMovieRating(movie, rating, comment, ratingUser, instanceSupplier)

        assertEquals(movie, result.movie)
        assertEquals(rating, result.rating)
        assertEquals(comment, result.comment)
        assertEquals(ratingUser, result.ratingUser)
    }

    @Test
    fun `createMovieRating should set ratingUser to Anonymous if null`() {
        val movie = object : Movie {
            override var id: String? = "movieId"
            override var title: String? = "movieTitle"
        }
        val rating: Short = 4
        val comment = "Great movie!"
        val movieRatingInstance = object : MovieRating {
            override var id: String? = null
            override var rating: Short? = null
            override var comment: String? = null
            override var ratingUser: String? = null
            override var movie: Movie? = null
        }

        val instanceSupplier = { movieRatingInstance }

        val result = createMovieRatingUseCase.createMovieRating(movie, rating, comment, null, instanceSupplier)

        assertEquals("Anonymous", result.ratingUser)
    }

    @Test
    fun `createMovieRating should throw InvalidStateException for invalid rating`() {
        val movie = object : Movie {
            override var id: String? = "movieId"
            override var title: String? = "movieTitle"
        }
        val rating: Short = 6
        val comment = "Great movie!"
        val ratingUser = "JohnDoe"

        val instanceSupplier = {
            object : MovieRating {
                override var id: String? = null
                override var rating: Short? = null
                override var comment: String? = null
                override var ratingUser: String? = null
                override var movie: Movie? = null
            }
        }

        assertThrows(InvalidStateException::class.java) {
            createMovieRatingUseCase.createMovieRating(movie, rating, comment, ratingUser, instanceSupplier)
        }
    }

    @Test
    fun `createMovieRating should throw BusinessRuleException for profaned ratingUser`() {
        val movie = object : Movie {
            override var id: String? = "movieId"
            override var title: String? = "movieTitle"
        }
        val rating: Short = 4
        val comment = "Great movie!"
        val ratingUser = "BadUser"

        `when`(profanityService.hasProfanedText(ratingUser)).thenReturn(true)

        val instanceSupplier = {
            object : MovieRating {
                override var id: String? = null
                override var rating: Short? = null
                override var comment: String? = null
                override var ratingUser: String? = null
                override var movie: Movie? = null
            }
        }

        assertThrows(BusinessRuleException::class.java) {
            createMovieRatingUseCase.createMovieRating(movie, rating, comment, ratingUser, instanceSupplier)
        }

        Mockito.verify(profanityService).hasProfanedText(ratingUser)
    }

    @Test
    fun `createMovieRating should throw BusinessRuleException for profaned comment`() {
        val movie = object : Movie {
            override var id: String? = "movieId"
            override var title: String? = "movieTitle"
        }
        val rating: Short = 4
        val comment = "This is bad!"
        val ratingUser = "JohnDoe"

        `when`(profanityService.hasProfanedText(comment)).thenReturn(true)

        val instanceSupplier = {
            object : MovieRating {
                override var id: String? = null
                override var rating: Short? = null
                override var comment: String? = null
                override var ratingUser: String? = null
                override var movie: Movie? = null
            }
        }

        assertThrows(BusinessRuleException::class.java) {
            createMovieRatingUseCase.createMovieRating(movie, rating, comment, ratingUser, instanceSupplier)
        }

        Mockito.verify(profanityService).hasProfanedText(comment)
    }

    @Test
    fun `createMovieRating should log warning if profanityService is null`() {
        val useCaseWithNullService = CreateMovieRatingUseCase(null)
        val movie = object : Movie {
            override var id: String? = "movieId"
            override var title: String? = "movieTitle"
        }
        val rating: Short = 4
        val comment = "Great movie!"
        val ratingUser = "JohnDoe"
        val movieRatingInstance = object : MovieRating {
            override var id: String? = null
            override var rating: Short? = null
            override var comment: String? = null
            override var ratingUser: String? = null
            override var movie: Movie? = null
        }

        val instanceSupplier = { movieRatingInstance }

        val result = useCaseWithNullService.createMovieRating(movie, rating, comment, ratingUser, instanceSupplier)

        assertEquals(movie, result.movie)
        assertEquals(rating, result.rating)
        assertEquals(comment, result.comment)
        assertEquals(ratingUser, result.ratingUser)
    }
}