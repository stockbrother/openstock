package daydayup.jdbc;

import java.sql.Connection;

public interface ConnectionOperation<T> {


	public T execute(Connection con) ;

}
