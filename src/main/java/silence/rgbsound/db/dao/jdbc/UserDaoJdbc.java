package silence.rgbsound.db.dao.jdbc;

import silence.rgbsound.db.User;
import silence.rgbsound.db.dao.UserDao;

public class UserDaoJdbc implements UserDao {
    @Override
    public User getDefaultUser() {
        return null;
    }
}
