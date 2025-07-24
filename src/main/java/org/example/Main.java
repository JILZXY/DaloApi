package org.example;
import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;
import org.example.di.AppModule;
//Este es tu metodo principal aqui tienes que crear todos tus inicializadores 'init'
/*para las validaciones tambien puedes crear una carpeta aparte y usar java.validation donde usando sus decoradores '@ejemplo'
podras usar y reciclar las validaciones para difenrentes modelos si estas se repiten varicas veces*/
public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });
        }).start(7000);

        // Rutas generales
        app.get("/", ctx -> ctx.result("API Javalin 2"));


        AppModule.initUsuario().register(app);
        AppModule.initRol().register(app);
        AppModule.initLocalidad().register(app);
        AppModule.initCalificaciones().register(app);
        AppModule.initConsultas().register(app);
        AppModule.initMotivoReporte().register(app);
        AppModule.initRespuestaConsulta().register(app);
        AppModule.initMensaje().register(app);
        AppModule.initChat().register(app);
        AppModule.initMaterias().register(app);
        AppModule.iniUsuerMateria().register(app);
        AppModule.initReporte().register(app);
        AppModule.initAbogado().register(app);
    }
}