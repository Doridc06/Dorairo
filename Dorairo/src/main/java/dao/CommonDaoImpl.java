package dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

/**
 * Common Dao interface implementation
 * 
 * @author JairoAB
 *
 * @param <T> Type of the object
 */
public abstract class CommonDaoImpl<T> implements CommonDaoI<T> {

	/** Database connection */
	private Session session;

	/** Class type */
	private Class<T> entityClass;

	/**
	 * CommonDaoImpl constructor
	 * 
	 * @param session Database session
	 */
	@SuppressWarnings("unchecked")
	protected CommonDaoImpl(Session session) {
		setEntityClass(
				(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		this.session = session;
	}

	@Override
	public void insert(T paramT) {
		checkActiveTransaction();

		// Stores the object, sends it and commits the changes
		session.save(paramT);
		session.flush();
		session.getTransaction().commit();
	}

	@Override
	public void update(T paramT) {
		checkActiveTransaction();

		// Updates the object and commits the changes
		session.saveOrUpdate(paramT);
		session.getTransaction().commit();
	}

	@Override
	public void delete(T paramT) {
		checkActiveTransaction();

		// Deletes the object and commits the changes
		session.delete(paramT);
		session.getTransaction().commit();
	}

	@Override
	public List<T> searchAll() {
		checkActiveTransaction();

		// Gets all objects and stores them in a list
		return session.createQuery("FROM " + this.entityClass.getName()).list();
	}

	@Override
	public void checkActiveTransaction() {
		if (session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE)) {
			session.beginTransaction();
		}
	}

	/**
	 * @return the entityClass
	 */
	public Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * @param entityClass the entityClass to set
	 */
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

}
