package org.voltdb.convert.common;

import org.voltdb.seutils.utils.SqlUtils;

public class OtherDbProcParameter {

	String paramName = null;
	
	String paramJavaType = null;

	 String voltDbType = null;

	public OtherDbProcParameter(String paramName, String paramJavaType, String voltDbType) {
		super();
		this.paramName = paramName;
		this.paramJavaType = paramJavaType;
		this.voltDbType = voltDbType;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamJavaType() {
		return paramJavaType;
	}

	public void setParamJavaType(String paramJavaType) {
		this.paramJavaType = paramJavaType;
	}
	
	@Override
	public String toString() {
		return paramJavaType + " " + SqlUtils.toJavaName(paramName);
	}

	public String toStringAsOutputVariable() {
		return paramJavaType + " " + SqlUtils.toJavaName(paramName) + ";";
	}
	
	public String toStringAsVoltTableAssign(int pos) {
		
		if (paramJavaType.equals("VoltTable")) {
		return "results[" + pos + "] = " + SqlUtils.toJavaName(paramName) + ";";
		}
		
		StringBuffer b = new StringBuffer();
		
		b.append("VoltTable t");
		b.append(pos);
		b.append(" = new VoltTable(");
		b.append(System.lineSeparator());
		
	    b.append("  new VoltTable.ColumnInfo(\"");
	    
	    b.append(paramName);
	    b.append("\" , ");
	    b.append(SqlUtils.getVoltDBDataTypeEnumerationName(voltDbType));
	    b.append("));");
		b.append(System.lineSeparator());
	    
 	    b.append("t");
        b.append(pos);
        b.append(".addRow(");
        b.append(SqlUtils.toJavaName(paramName));
        b.append(");");
		b.append(System.lineSeparator());
		
		b.append("results[");
		b.append(pos);
		b.append("] = t");
        b.append(pos);
		b.append(";");
		b.append(System.lineSeparator());
		
		return b.toString();
	}
	
	public String toStringAsSoleOutput() {
		return "return " + SqlUtils.toJavaName(paramName) + ";";
	}


	
}
