package com.example.pokedex_2_0.data.responce

import com.example.pokedex_2_0.data.responce.GenerationI
import com.example.pokedex_2_0.data.responce.GenerationIi
import com.example.pokedex_2_0.data.responce.GenerationIii
import com.example.pokedex_2_0.data.responce.GenerationIv
import com.example.pokedex_2_0.data.responce.GenerationV
import com.example.pokedex_2_0.data.responce.GenerationVi
import com.example.pokedex_2_0.data.responce.GenerationVii
import com.example.pokedex_2_0.data.responce.GenerationViii

data class Versions(
    val generationi: GenerationI,
    val generationii: GenerationIi,
    val generationiii: GenerationIii,
    val generationiv: GenerationIv,
    val generationv: GenerationV,
    val generationvi: GenerationVi,
    val generationvii: GenerationVii,
    val generationviii: GenerationViii
)