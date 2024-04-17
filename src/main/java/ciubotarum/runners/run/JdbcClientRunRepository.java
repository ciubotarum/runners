package ciubotarum.runners.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRunRepository {
    //    private List<Run> runs = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(JdbcClientRunRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return jdbcClient.sql("select * from run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById(Integer id) {
        return jdbcClient.sql("SELECT id, title, started_on, completed_on, miles, location FROM Run WHERE id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    // CREATE
    public void create(Run run) {
//        var updated = jdbcClient.sql("INSERT INTO Run(id, title, started_on, completed_on, miles, location) values(?,?,?,?,?,?) ")
//                .param(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
//                .update();

        var updated = jdbcClient.sql("INSERT INTO Run(id, title, started_on, completed_on, miles, location) VALUES(:id, :title, :startedOn, :completedOn, :miles, :location)")
                .param("id", run.id())
                .param("title", run.title())
                .param("startedOn", run.startedOn())
                .param("completedOn", run.completedOn())
                .param("location", run.location().toString())
                .param("miles", run.miles())
                .update();


        Assert.state(updated == 1, "Failed to create run " + run.title());
    }

    // UPDATE
    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("UPDATE run SET title = :title, started_on = :startedOn, completed_on = :completedOn, miles = :miles, location = :location WHERE id = :id ")
                .param("title", run.title())
                .param("startedOn", run.startedOn())
                .param("completedOn", run.completedOn())
                .param("miles", run.miles())
                .param("location", run.location().toString())
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to update run " + run.title());
    }

    // DELETE
    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from run where id = :id ")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete run " + id);
    }

    public int count() {
        return (int) jdbcClient.sql("select * from run").query(Run.class).stream().count();
    }
    public void saveAll(List<Run> runs) {
        runs.stream().forEach(this::create);
    }

    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("select * from Run where location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }

}
