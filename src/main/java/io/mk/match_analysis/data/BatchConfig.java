package io.mk.match_analysis.data;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import io.mk.match_analysis.model.Match;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final String[] FIELD_NAMES = new String[] {
            "ID", "City", "date1", "Season", "MatchNumber", "Team1", "Team2", "Venue", "TossWinner", "TossDecision",
            "SuperOver", "WinningTeam", "WonBy", "Margin", "method", "Player_of_Match", "Team1Players", "Team2Players",
            "Umpire1", "Umpire2"

    };

    @Bean
    public FlatFileItemReader<matchInput> reader() {
        return new FlatFileItemReaderBuilder<matchInput>()
                .name("matchItemReader")
                .resource(new ClassPathResource("match-data.csv"))
                .delimited()
                .names(FIELD_NAMES) // read all data feilds
                .fieldSetMapper(new BeanWrapperFieldSetMapper<matchInput>() {
                    {
                        setTargetType(matchInput.class);
                    }
                })
                .build();
    }

    @Bean
    public MatchDataProcessor processor() {
        return new MatchDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {

        JdbcBatchItemWriter<Match> writer = new JdbcBatchItemWriterBuilder<Match>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO match (id,city,date1,deason,match_number,team1,team2,venue,toss_winner,toss_decision,super_over,winning_team,won_by,margin,method,player_of_match,team1_players,team2_players,umpire1,umpire2) VALUES (:ID,:City,:date1,:Season,:MatchNumber,:Team1,:Team2,:Venue,:TossWinner,:TossDecision,:SuperOver,:WinningTeam,:WonBy,:Margin,:method,:PlayerOfMatch,:Team1Players,:Team2Players,:Umpire1,:Umpire2)")
                .dataSource(dataSource)
                .build();

        return writer;
    }

    @Bean
    Job importUserJob(JobRepository jobRepository,
            JobCompletionNotificationListener listener, Step step1) {

        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    Step step1(JobRepository jobRepository,
            PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Match> writer) {
        return new StepBuilder("step1", jobRepository)
                .<matchInput, Match>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
