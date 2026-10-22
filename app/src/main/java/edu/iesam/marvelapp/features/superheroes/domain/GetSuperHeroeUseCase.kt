package edu.iesam.marvelapp.features.superheroes.domain

class GetSuperHeroeUseCase (private val superHeroRepository: SuperHeroRepository) {

    operator fun invoke() : List<SuperHero>{
        return superHeroRepository.getAll()
    }

}