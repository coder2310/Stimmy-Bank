//package stimmybank.com.stimmybank.config;
//
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DatasourceConfig {
//    @Bean
//    public DataSource datasource(){
//        return DataSourceBuilder.create()
//                .url("jdbc:mysql://localhost:8889/bank")
//                .username("root")
//                .password("root")
//                .
//                .build();
//    }
//    @Bean
//    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
//        return new NamedParameterJdbcTemplate(this.datasource());
//    }
//}
//
