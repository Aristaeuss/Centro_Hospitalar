package b3.CentroHospitalar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    CustomAuthenticatorProvider customAuthenticatorProvider;

    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] doctorsPages={
            "/VistaGeralMedico",
            "/MedicoConsulta",
            "/perfilMedicos",
            "/consultasMedico",
            "/contactosMedico",
            "/scheduleMedico",
            "/infoMedico",
            "/utentesEsperaMedico",
            "/listaConsultasMedico",
            "/getNextTicket",
            "/callTicketAgain",
            "/absentPatient",
            "/chamarSenha/**",
            "/acceptTicket",
            "/waitTicket",
            "/rejectTicket",
            "/saveAppointment",
            "/perfilMedico",
            "/alterarMoradaM",
            "/alterarTelefoneM",
            "/alterarImagemM",
            "/alterarNomeM",
            "/alterarSobre",
            "/horariosMedico",
            "/utentesMedico",
            "/pesquisaUtenteM",
            "/listaUtentesMedicoHoje",
            "/historicoUtente/**",
            "/detalhesConsultaMedico/**",
            "/perfilUtenteStatic/**",
            "/perfilMedicoStatic/**"
    };

    private static final String[] patientPages={
            "/VistaGeralUtente",
            "/perfilUtente",
            "/calendarioUtente",
            "/calendarioUtenteE",
            "/calendarioUtenteM",
            "/calendarioUtente/**",
            "/calendarioUtenteL/**",
            "/consultaMarcada/**",
            "/VistaGeralUtente",
            "/consultasUtente",
            "/listaConsultasCanceladasUtente",
            "/listaConsultasPreviasUtente",
            "/listaConsultasUtente",
            "/listaFaturasUtente",
            "/listaFaturasUtente/**",
            "/cancelarConsultaUtente/**",
            "/remarcarConsultaUtente/**",
            "/remarcarConsulta/**",
            "/medicosUtente",
            "/faturasUtente",
            "/perfilUtente",
            "/contactosUtente",
            "/infoUtente",
            "/perfilUtente",
            "/alterarMoradaU",
            "/alterarTelefoneU",
            "/alterarImagemU",
            "/alterarNomeU",
            "/userListMedicos",
            "/userListMedicosSpeciality"
    };

    private static final String[] employeePages = {
            "/VistaGeralRecepcao",
            "/listaFaturasFuncionario",
            "/listaFaturasFuncionario/**",
            "/pagarFaturaFuncionario/**",
            "/listaUtentesEmEsperaFuncionario",
            "/medicos",
            "/listaMedicosPresentesFuncionario",
            "/consultasFuncionario",
            "/verConsultasFuncionario",
            "/verConsultasFuncionario/**",
            "/cancelarConsultaFuncionario/**",
            "/listaMedicosFuncionario",
            "/ListaEspera",
            "/utentesFuncionario",
            "/listaUtentesAtrasadosFuncionario",
            "/pesquisaUtenteNIF",
            "/pesquisaUtenteF",
            "/perfilFuncionario",
            "/alterarMoradaF",
            "/alterarTelefoneF",
            "/alterarImagemF",
            "/alterarNomeF",
            "/Registos",
            "/Registos/Utente",
            "/registoFuncionarioU",
            "/checkIn",
            "/perfilUtenteStatic/**",
            "/perfilMedicoStatic/**",
            "/calendarioFuncionario",
            "/calendarioFuncionarioE",
            "/calendarioFuncionarioM",
            "/calendarioFuncionario/**",
            "/calendarioFuncionarioL/{dateDoctor}",
            "/marcarConsultaFuncionario/**",
            "/consultaMarcadaFuncionario/**",
            "/remarcarConsultaFuncionario/**",
            "/remarcarConsultaFuncionarioChooseDay/**",
            "/contactosFuncionario",
            "/infoFuncionario"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/JS/**","/", "/landingPage","/registo", "/CSS/**", "/Images/**","/UploadedImages/**","/login","/alterarPassword","/checkInMaquina","/SelfCheckIn","/EcraSenhasChamadas","/listMedicos","/listMedicosSpeciality","/recuperarAcesso", "/alterarPassword/**", "/alterarPasswordp").permitAll()
                .antMatchers(doctorsPages).hasRole("DOCTOR")
                .antMatchers(patientPages).hasRole("PATIENT")
                .antMatchers(employeePages).hasRole("EMPLOYEE")
                .antMatchers("/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(customSuccessHandler)
                .failureUrl("/login?error=true")
                .and()
                .logout()
                .logoutSuccessUrl("/landingPage");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
            .authenticationProvider(customAuthenticatorProvider);
    }

}
