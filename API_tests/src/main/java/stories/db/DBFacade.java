package stories.db;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import stories.db.mapper.JobMappers;
import stories.model.shademodel.db.JobsDBModel;
import stories.model.shademodel.db.UsersDBModel;
import stories.db.mapper.UserMappers;
import stories.model.custom.BackendSettings;

import java.sql.ResultSet;
import java.util.List;

/**
 * Facade to the specific database.
 * You can create as many DB facades as you needed and they can point to the different instances.
 */
public class DBFacade extends DBDao {
    /**
     * Create new DB facade.
     *
     * @param backendSettings containing setting needed to establish connection to the specific DB instance.
     */
    public DBFacade(BackendSettings backendSettings) {
        super(backendSettings);
    }

    // Example method.
    public UsersDBModel getUserById(final int id) {
        String sql = "SELECT * FROM \"public\".\"User\" WHERE \"Id\" = ?";
        return jdbcTemplatePg.queryForObject(sql, new UserMappers.UserRowMapper(), id);
    }

    public int getIdUserByEmail(String email) {
        String sql = "SELECT * FROM \"public\".\"User\" WHERE \"IsDeleted\" = false and \"Email\" = ?";
    /*    UsersDBModel user = jdbcTemplatePg.queryForObject(sql, new UserMappers.UserRowMapper(), email);
        return user.getId();
        EmptyResultDataAccessException(1)
    }*/
        try {
            UsersDBModel user = jdbcTemplatePg.queryForObject(sql, new UserMappers.UserRowMapper(), email);
            return user.getId();
        }
        catch (EmptyResultDataAccessException err) {
            return 0;
        }
    }

    //Delete user from BD
    public UsersDBModel UserDBDelete(String id){
        String sql = "DELETE * FROM \"public\".\"User\" WHERE \"Id\" = ?";
        return jdbcTemplatePg.queryForObject(sql, new UserMappers.UserRowMapper(), id);
    }

    // получить кол-во необходимых запичсей
    //скорее всего проще будет передавать уже готовую строку.
    public Integer JobCount(String sqlRequest){

       // String sql = "SELECT COUNT (*) FROM public.\"Job\" WHERE \"" + field + "\"" + " = '" +  value + "'";
        String sql = "SELECT COUNT (*) FROM public.\"Job\" WHERE " + sqlRequest;
        try {
            int count = 0;

//            return jdbcTemplatePg.queryForObject(sql, new JobMappers.JobRowMapper(), count);
            SqlRowSet set = jdbcTemplatePg.queryForRowSet(sql);
            if (set.next()) {
                count = set.getInt("count");
            }
            return count;


        }
        catch (EmptyResultDataAccessException err) {
            return 0;
        }
    }

    public Integer JobCountForRating(String rating){

        // String sql = "SELECT COUNT (*) FROM public.\"Job\" WHERE \"" + field + "\"" + " = '" +  value + "'";
        String sql = "select count(1) from\n" +
                "(select *, \"ReviewsSum\" / \"ReviewsCount\" as \"Rating\" from \"User\") as \"tmp\"\n" +
                "where \"tmp\".\"Rating\" >  " + rating + " )";


        try {
            int count = 0;

//            return jdbcTemplatePg.queryForObject(sql, new JobMappers.JobRowMapper(), count);
            SqlRowSet set = jdbcTemplatePg.queryForRowSet(sql);
            if (set.next()) {
                count = set.getInt("count");
            }
            return count;


        }
        catch (EmptyResultDataAccessException err) {
            return 0;
        }
    }


}

