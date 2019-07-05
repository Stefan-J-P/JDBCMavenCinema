package maven.jdbc.cinema.connection;



import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlTableCommand
{
    private List<String> commands;

    // ARG CONSTRUCTOR -------------------------------------------------------------------------------------
    private SqlTableCommand(SqlTableCommandBuilder sqlTableCommandBuilder)
    {
        this.commands = sqlTableCommandBuilder.commands;
    }

    // NO ARG CONSTRUCTOR ----------------------------------------------------------------------------------
    public static SqlTableCommandBuilder builder()
    {
        return new SqlTableCommandBuilder();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(commands.get(0));
        sb.append(commands.stream().skip(1).collect(Collectors.joining(", ")));
        sb.append(");");
        return sb.toString();
    }



    public static class SqlTableCommandBuilder
    {
        private List<String> commands = new ArrayList<>();

        // CREATE TABLE -------------------------------------------------------------------------------------
        public SqlTableCommandBuilder table(String name)
        {
            try
            {
                if (commands.isEmpty())
                {
                    commands.add(MessageFormat.format("create table if not exists {0} ( ", name));
                }
                return this;
            } catch (Exception e)
            {
                e.printStackTrace();
                throw new MyException(ExceptionCode.BUILDER, "TABLE COMMAND EXCEPTION: " + e.getMessage());
            }
        }


        // PRIMARY KEY ---------------------------------------------------------------------------------------
        public SqlTableCommandBuilder primaryKey(String name)
        {
            try
            {
                if (commands.size() == 1)
                {
                    commands.add(MessageFormat.format("{0} integer primary key autoincrement ", name));
                }
                return this;
            } catch (Exception e)
            {
                e.printStackTrace();
                throw new MyException(ExceptionCode.BUILDER, "PRIMARY KEY COMMAND EXCEPTION: " + e.getMessage());
            }
        }

        // INT COLUMN ----------------------------------------------------------------------------------------
        public SqlTableCommandBuilder intColumn(String name, String options)
        {
            try
            {
                if (commands.size() >= 2)
                {
                    commands.add(MessageFormat.format("{0} integer {1} ", name, options));
                }
                return this;

            } catch (Exception e)
            {
                e.printStackTrace();
                throw new MyException(ExceptionCode.BUILDER, "INT COLUMN COMMAND EXCEPTION: " + e.getMessage());
            }
        }

        // STRING COLUMN --------------------------------------------------------------------------------------
        public SqlTableCommandBuilder stringColumn(String name, int length, String options)
        {
            try
            {
                if (commands.size() >= 2)
                {
                    commands.add(MessageFormat.format("{0} varchar({1}) {2} ", name, length, options));
                }
                return this;

            } catch (Exception e)
            {
                e.printStackTrace();
                throw new MyException(ExceptionCode.BUILDER, "STRING COLUMN COMMAND EXCEPTION: " + e.getMessage());
            }
        }

        // DECIMAL COLUMN -------------------------------------------------------------------------------------
        public SqlTableCommandBuilder decimalColumn(String name, int precision, int scale, String options)
        {
            try
            {
                if (commands.size() >= 2)
                {
                    commands.add(MessageFormat.format("{0} decimal({1}, {2}) {3} ", name, precision, scale, options));
                }
                return this;

            } catch (Exception e)
            {
                e.printStackTrace();
                throw new MyException(ExceptionCode.BUILDER, "DECIMAL COLUMN COMMAND EXCEPTION: " + e.getMessage());
            }
        }

        // TIMESTAMP ------------------------------------------------------------------------------------------
        public SqlTableCommandBuilder dateTimeColumn(String name, String options)
        {
            try
            {
                if (commands.size() >= 2)
                {
                    commands.add(MessageFormat.format("{0} timestamp {1} ", name, options));
                }
                return this;

            } catch (Exception e)
            {
                e.printStackTrace();
                throw new MyException(ExceptionCode.BUILDER, "TIMESTAMP (dateTime) COLUMN COMMAND EXCEPTION: " + e.getMessage());
            }
        }


        // COLUMN ---------------------------------------------------------------------------------------------
        public SqlTableCommandBuilder column(String name, String type, String options)
        {
            try
            {
                if (commands.size() >= 2)
                {
                    commands.add(MessageFormat.format("{0} {1} {2} ", name, type, options));
                }
                return this;

            } catch (Exception e)
            {
                e.printStackTrace();
                throw new MyException(ExceptionCode.BUILDER, "COLUMN COMMAND EXCEPTION: " + e.getMessage());
            }
        }

        // FOREIGN KEY -----------------------------------------------------------------------------------------
        public SqlTableCommandBuilder foreignKey(String column, String foreignTable, String foreignColumn, String options)
        {
            try
            {
                if (commands.size() >= 2)
                {
                    commands.add(MessageFormat.format("foreign key ({0}) references {1}({2}) {3} ", column, foreignTable, foreignColumn, options));
                }
                return this;

            } catch (Exception e)
            {
                e.printStackTrace();
                throw new MyException(ExceptionCode.BUILDER, "FOREIGN KEY COLUMN COMMAND EXCEPTION: " + e.getMessage());
            }
        }

        // RETURN NEW SQL TABLE COMMAND -------------------------------------------------------------------------
        public SqlTableCommand builder()
        {
            return new SqlTableCommand(this);
        }
    }

}








































