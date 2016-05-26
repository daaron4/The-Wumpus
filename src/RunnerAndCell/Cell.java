package RunnerAndCell;
/*
 * First and Last name: Kal Cramer and David Aaron
 * Assignment name: Hunt the Wumpus: Iteration 2
 * Date due:9/19/14
 */
public class Cell
{
  public enum Type
  {
    Blood, Slime, Goop, Pit, Wumpus, Nothing, DeadWumpus
  }

  private Type ty;
  private boolean visablity;

  public Cell(Type atype)
  {
    this.ty = atype;
    visablity = false;
  }

  public void setType(Type atype)
  {
    ty = atype;
  }

  public Type getType()
  {
    return ty;
  }

  public boolean isVisable()
  {
    return visablity;
  }

  public void changeVisable()
  { 
	visablity = true;
  }
  public void changeVisable2(Type type)
  { 
	if (type != Cell.Type.DeadWumpus) 
		visablity = false;
  }
}
