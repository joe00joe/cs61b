public class Planet {
	public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public  static  final double G = 6.67e-11;
	
	public Planet(double xP, double yP, double xV,
              double yV, double m, String img){
				  xxPos=xP;
				  yyPos=yP;
				  xxVel=xV;
				  yyVel=yV;
				  mass=m;
				  imgFileName=img;
	}
	
	public Planet(Planet p){
		xxPos=p.xxPos;
		yyPos=p.yyPos;
		xxVel=p.xxVel;
		yyVel=p.yyVel;
		mass=p.mass;
		imgFileName=p.imgFileName;
	}
	
	public double calcDistance(Planet other){
		double dx=other.xxPos-xxPos;
		double dy=other.yyPos-yyPos;
		return Math.sqrt(dx*dx+dy*dy);
	}

	public double calcForceExertedBy(Planet other){
    	double r =this.calcDistance(other);
    	return G*this.mass*other.mass/(r*r);
	}


	public double calcForceExertedByX(Planet other){
		double dx=other.xxPos-xxPos;
		double r =this.calcDistance(other);
		double F=this.calcForceExertedBy(other);
		return F*dx/r;

	}

	public double calcForceExertedByY(Planet other){
		double dy=other.yyPos-yyPos;
		double r =this.calcDistance(other);
		double F=this.calcForceExertedBy(other);
		return F*dy/r;
	}

	public double calcNetForceExertedByX(Planet [] allPlanets){
		double netForceX=0;
		for(int i=0;i<allPlanets.length;i++){
			if (this ==allPlanets[i]){
				continue;
			}
			netForceX+=this.calcForceExertedByX(allPlanets[i]);
		}
		return netForceX;
	}
    
	public double calcNetForceExertedByY(Planet [] allPlanets){
		double netForceY=0;
		for(int i=0;i<allPlanets.length;i++){
			if (this ==allPlanets[i]){
				continue;
			}
			netForceY+=this.calcForceExertedByY(allPlanets[i]);
		}
		return netForceY;
	}
	
	public void update(double dt,double netForceX,double netForceY){
	    double aNetX=netForceX/this.mass;
		double aNetY=netForceY/this.mass;
		this.xxVel+=aNetX*dt;
		this.yyVel+=aNetY*dt;
		this.xxPos+=this.xxVel*dt;
		this.yyPos+=this.yyVel*dt;
	}
	
	public void draw(){
		StdDraw.picture(xxPos, yyPos, "images/"+imgFileName);
	}
}