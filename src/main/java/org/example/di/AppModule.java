package org.example.di;

import org.example.controller.*;
import org.example.repository.*;
import org.example.routes.*;
import org.example.service.*;

public class AppModule {

    public static RolRutes initRol() {
        RolRepository rolRepository = new RolRepository();
        RolService rolService = new RolService(rolRepository);
        RolController rolController = new RolController(rolService);
        RolRutes rolRutes = new RolRutes(rolController);
        return rolRutes;
    }

    public static LocalidadRoutes initLocalidad(){
        LocalidadRepository localidadRepository = new LocalidadRepository();
        LocalidadService localidadService = new LocalidadService(localidadRepository);
        LocalidadController localidadController = new LocalidadController(localidadService);
        LocalidadRoutes localidadRoutes = new LocalidadRoutes(localidadController);
        return localidadRoutes;
    }

    public static UsuarioRoutes initUsuario(){
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        UsuarioRoutes usuarioRoutes = new UsuarioRoutes(usuarioController);
        return usuarioRoutes;
    }

    public static CalificacionesRoutes initCalificaciones(){
        CalificacionRepository calificacionRepository = new CalificacionRepository();
        CalificacionService calificacionService = new CalificacionService(calificacionRepository);
        CalificacionController calificacionController = new CalificacionController(calificacionService);
        CalificacionesRoutes calificacionesRoutes = new CalificacionesRoutes(calificacionController);
        return calificacionesRoutes;
    }

    public static ConsultaRoutes initConsultas(){
        ConsultaRepository consultaRepository = new ConsultaRepository();
        ConsultaService consultaService = new ConsultaService(consultaRepository);
        ConsultaController consultaController = new ConsultaController(consultaService);
        ConsultaRoutes consultaRoutes = new ConsultaRoutes(consultaController);
        return consultaRoutes;
    }

    public static MotivoReporteRoutes initMotivoReporte(){
        MotivoReporteRepository motivoReporteRepository = new MotivoReporteRepository();
        MotivoReporteService motivoReporteService = new MotivoReporteService(motivoReporteRepository);
        MotivoReporteController motivoReporteController =new MotivoReporteController(motivoReporteService);
        MotivoReporteRoutes motivoReporteRoutes=new MotivoReporteRoutes(motivoReporteController);
        return motivoReporteRoutes;
    }

    public static RespuestaConsultaRouste initRespuestaConsulta(){
        RespuestaConsultaRepository respuestaConsultaRepository =new RespuestaConsultaRepository();
        RespuestaConsultaService respuestaConsultaService=new RespuestaConsultaService(respuestaConsultaRepository);
        RespuestaConsultaController respuestaConsultaController=new RespuestaConsultaController(respuestaConsultaService);
        RespuestaConsultaRouste respuestaConsultaRouste=new RespuestaConsultaRouste(respuestaConsultaController);
        return respuestaConsultaRouste;
    }

    public static MensajeRoutes initMensaje(){
        MensajeRepository mensajeRepository =new MensajeRepository();
        MensajeService mensajeService=new MensajeService(mensajeRepository);
        MensajeController mensajeController=new MensajeController(mensajeService);
        MensajeRoutes mensajeRoutes=new MensajeRoutes(mensajeController);
        return mensajeRoutes;
    }

    public static ChatRoutes initChat(){
        ChatRepository chatRepository =new ChatRepository();
        ChatService chatService=new ChatService(chatRepository);
        ChatController chatController=new ChatController(chatService);
        ChatRoutes chatRoutes=new ChatRoutes(chatController);
        return chatRoutes;
    }

    public static MateriaRoutes initMaterias(){
        MateriaRepository materiaRepository=new MateriaRepository();
        MateriaService materiaService=new MateriaService(materiaRepository);
        MetariaController metariaController=new MetariaController(materiaService);
        MateriaRoutes materiaRoutes=new MateriaRoutes(metariaController);
        return materiaRoutes;
    }

    public static UsuarioMateriaRoutes iniUsuerMateria(){
        UsuarioMateriaRepository usuarioMateriaRepository=new UsuarioMateriaRepository();
        UsuarioMateriaService usuarioMateriaService=new  UsuarioMateriaService(usuarioMateriaRepository);
        UsuarioMateriaController usuarioMateriaController = new UsuarioMateriaController(usuarioMateriaService);
        UsuarioMateriaRoutes usuarioMateriaRoutes = new UsuarioMateriaRoutes(usuarioMateriaController);
        return usuarioMateriaRoutes;
    }

    public static ReporteRoutes initReporte(){
        ReporteRepository reporteRepository = new ReporteRepository();
        ReporteService reporteService = new ReporteService(reporteRepository);
        Reportecontroller reportecontroller = new Reportecontroller(reporteService);
        ReporteRoutes reporteRoutes = new ReporteRoutes(reportecontroller);
        return reporteRoutes;
    }

    public static AbogadoRoutes initAbogado(){
        AbogadoRepository abogadoRepository=new AbogadoRepository();
        AbogadoService abogadoService=new AbogadoService(abogadoRepository);
        AbogadoController abogadoController=new AbogadoController(abogadoService);
        AbogadoRoutes abogadoRoutes=new AbogadoRoutes(abogadoController);
        return  abogadoRoutes;
    }
}
