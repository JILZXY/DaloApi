package org.example.service;

import java.util.Set;

public class FiltroLenguaje {

    private static final Set<String> PALABRAS_PROHIBIDAS = Set.of(
            "puta", "puto", "mierda", "cabron", "cabrona", "pendejo", "pendeja", "estupido", "estupida", "imbecil",
            "idiota", "maldito", "maldita", "chingar", "chingada", "coño", "culero", "culera", "jodido", "jodida",
            "zorra", "perra", "marica", "verga", "cabrón", "gilipollas", "hijo de puta", "cagada", "huevon", "huevona",
            "joder", "jodio", "naco", "naca", "chingas", "hija de puta", "mampo", "mampa", "coyol", "bastardo", "bastarda",
            "joto", "pedazo de mierda"

    );

    public static boolean contieneGroserias(String texto) {
        if (texto == null) return false;

        String textoNormalizado = texto.toLowerCase().replaceAll("[^a-záéíóúñü\\s]", "");
        for (String palabra : textoNormalizado.split("\\s+")) {
            if (PALABRAS_PROHIBIDAS.contains(palabra)) {
                return true;
            }
        }
        return false;
    }
}
