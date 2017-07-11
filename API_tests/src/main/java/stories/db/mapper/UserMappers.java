package stories.db.mapper;

import stories.db.DBFacade;
import stories.model.shademodel.db.UsersDBModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Class that contains RowMapper classes responsible for mapping of complex responses from database.
 * See {@link DBFacade} for usage example.
 */
public class UserMappers {
    private UserMappers() {
        throw new UnsupportedOperationException("You try to create instance of utility class.");
    }

    public static class UserRowMapper implements RowMapper<UsersDBModel> {
        @Override
        public UsersDBModel mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            UsersDBModel model = new UsersDBModel();

            model.setId(rs.getInt("Id"));
            model.setType(rs.getString("Type"));
            model.setEmail(rs.getString("Email"));
            model.setHashedPassword(rs.getString("HashedPassword"));
            model.setCreatedDateTime(rs.getString("CreatedDateTime"));
            model.setFbUid(rs.getString("FbUid"));
            model.setIsDeleted((Boolean) rs.getObject("IsDeleted"));
            model.setVerificationOption(rs.getString("VerificationOptions"));
            model.setLinkedinUid(rs.getString("LinkedinUid"));
            model.setReviewsSum(rs.getString("ReviewsSum"));
            model.setReviewsCounte(rs.getString("ReviewsCount"));
            model.setAvatarFileName(rs.getString("AvatarFileName"));
            model.setFirstName(rs.getString("FirstName"));
            model.setLastName(rs.getString("LastName"));
            model.setTiedUserId(rs.getString("TiedUserId"));

            return model;
        }
    }
}
