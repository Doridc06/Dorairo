package dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;

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
		setEntityClass((Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		this.session = session;
	}

	@Override
	public void insert(T paramT) {
		activeTransaction();

		// Stores the object, sends it and commits the changes
		session.save(paramT);
		session.flush();
		session.getTransaction().commit();
	}

	@Override
	public void update(T paramT) {
		activeTransaction();

		// Updates the object and commits the changes
		session.saveOrUpdate(paramT);
		session.flush();
		session.getTransaction().commit();
	}

	@Override
	public void delete(T paramT) {
		activeTransaction();

		// Deletes the object and commits the changes
		session.delete(paramT);
		session.flush();
		session.getTransaction().commit();
	}

	@Override
	public List<T> searchAll() {
		activeTransaction();

		// Gets all objects and stores them in a list
		return session.createQuery("FROM " + this.entityClass.getName()).list();
	}

	@Override
	public void activeTransaction() {
		try {
			session.beginTransaction();
		} catch (Exception e) {
			System.out.println();
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
