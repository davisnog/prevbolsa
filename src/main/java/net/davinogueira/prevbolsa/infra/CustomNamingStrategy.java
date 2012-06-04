package net.davinogueira.prevbolsa.infra;

import org.hibernate.cfg.DefaultNamingStrategy;

public class CustomNamingStrategy extends DefaultNamingStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String tableName(String tableName) {
		return underscoreString(tableName);
	}
	
	@Override
	public String propertyToColumnName(String propertyName) {
		return underscoreString(propertyName);
	}

	@Override
	public String classToTableName(String className) {
		return underscoreString(className);
	}
	
	@Override
	public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
		String forenKey = super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName, referencedColumnName);
		return underscoreString(forenKey) + "_id";
	}
	
	@Override
	public String joinKeyColumnName(String joinedColumn, String joinedTable) {
		String colunm = super.joinKeyColumnName(joinedColumn, joinedTable);
		return underscoreString(colunm);
	}
	
	private String underscoreString(String str){
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			
			if(i > 0 && Character.isUpperCase(c)){
				sb.append("_");
				sb.append(c);
			}else{
				sb.append(c);
			}
		}
		return sb.toString().toLowerCase();
	}
}
