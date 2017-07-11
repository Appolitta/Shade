package stories.db.mapper;

import stories.db.DBFacade;
import stories.model.shademodel.db.JobsDBModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
/**
 * Created by wizard on 23.06.2017.
 */
public class JobMappers {
    private JobMappers () { throw new UnsupportedOperationException("You try to create instance of utility class.");}

    public static class JobRowMapper  implements RowMapper<JobsDBModel> {
        @Override
        public JobsDBModel mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            JobsDBModel model = new JobsDBModel();

            model.setId(rs.getInt("Id"));
            model.setTitle(rs.getString("Name"));
            model.setSummary(rs.getString("Summary"));
            model.setSalary(rs.getInt("Salary"));
            model.setPosterId(rs.getInt("PosterId"));
            model.setStatus(rs.getInt("Status"));
            model.setLogoFileName(rs.getString("LogoFileName"));
            model.setCategoryId(rs.getInt("CategoryId"));
            model.setDeleted((Boolean) rs.getObject("IsDeleted"));
            model.setLocationId(rs.getInt("LocationId"));
            model.setDescription(rs.getString("Description"));
            model.setSalaryType(rs.getInt("SalaryType"));

            return model;
        }
    }


}
