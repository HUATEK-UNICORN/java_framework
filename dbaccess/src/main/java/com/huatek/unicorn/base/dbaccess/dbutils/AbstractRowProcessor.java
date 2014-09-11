package com.huatek.unicorn.base.dbaccess.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.dbutils.RowProcessor;

public abstract class AbstractRowProcessor implements RowProcessor {

	@Override
	public Object[] toArray(ResultSet rs) throws SQLException {
		throw new UnsupportedOperationException(
				"Method: \"Object[] toArray(ResultSet rs)\" is not supported.");
	}

	@Override
	public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException(
				"Method: \"T toBean(ResultSet rs, Class<T> type)\" is not supported.");
	}

	@Override
	public <T> List<T> toBeanList(ResultSet rs, Class<T> type)
			throws SQLException {
		throw new UnsupportedOperationException(
				"Method: \"List<T> toBeanList(ResultSet rs, Class<T> type)\" is not supported.");
	}

	@Override
	public Map<String, Object> toMap(ResultSet rs) throws SQLException {

		throw new UnsupportedOperationException(
				"Method: \"Map<String, Object> toMap(ResultSet rs)\" is not supported.");
	}

	protected static class CaseInsensitiveHashMap<V> extends HashMap<String, V> {

		/**
		 * Required for serialization support.
		 * 
		 * @see java.io.Serializable
		 */
		private static final long serialVersionUID = -2848100435296897392L;

		CaseInsensitiveHashMap(int cap) {
			super(cap);
		}

		/** {@inheritDoc} */
		@Override
		public boolean containsKey(Object key) {
			return super
					.containsKey(key.toString().toLowerCase(Locale.ENGLISH));
		}

		/** {@inheritDoc} */
		@Override
		public V get(Object key) {
			return super.get(key.toString().toLowerCase(Locale.ENGLISH));
		}

		/** {@inheritDoc} */
		@Override
		public V put(String key, V value) {
			return super.put(key.toString().toLowerCase(Locale.ENGLISH), value);
		}

		/** {@inheritDoc} */
		@Override
		public void putAll(Map<? extends String, ? extends V> m) {
			for (String key : m.keySet()) {
				this.put(key, m.get(key));
			}
		}

		/** {@inheritDoc} */
		@Override
		public V remove(Object key) {
			return super.remove(key.toString().toLowerCase(Locale.ENGLISH));
		}
	}
}
