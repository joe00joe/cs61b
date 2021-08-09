public class NBody{
	public static double readRadius(String fileName){
		In in = new In(fileName);
		double planetNum=in.readDouble();
		double radius=in.readDouble();
		return radius;
	}
	
	public static Planet [] readPlanets(String fileName){
		In in = new In(fileName);
		Planet [] allPlanets=new Planet[5];
	    in.readLine();
		in.readLine();
		
	    for(int i=0;i<5;i++){
			double xP=in.readDouble();
			double yP=in.readDouble();
			double xV=in.readDouble();
			double yV=in.readDouble();
			double m=in.readDouble();
			String img=in.readString();
			allPlanets[i]=new Planet(xP,yP,xV,yV,m,img);
		}
		return allPlanets;
		
	}
	
	public static void main(String [] args){
		if (args.length != 3) {
			System.out.println("Please imput there command line args:T,dt,fileName");
			return ;
		}	
		double T = Double.parseDouble(args[0]);
		double dt= Double.parseDouble(args[1]);
		String fileName=args[2];
		Planet [] allPlanets=readPlanets(fileName);
		double radius=readRadius(fileName);
		
		StdDraw.setScale(-radius, radius);
		String backImg="images/starfield.jpg";
		StdDraw.clear();
		StdDraw.picture(0,0,backImg);
		
	    for (Planet p:allPlanets){
			p.draw();
		}
		int waitTimeMilliseconds = 10;
	    for (double t=0;t<T;t+=dt){
			double [] xForces=new double[5];
			double [] yForces=new double[5];
			for(int i=0;i<5;i++){
				xForces[i]=allPlanets[i].calcNetForceExertedByX(allPlanets);
				yForces[i]=allPlanets[i].calcNetForceExertedByY(allPlanets);
			}
			for(int i=0;i<5;i++){
				allPlanets[i].update(dt,xForces[i],yForces[i]);
			}
			StdDraw.picture(0,0,backImg);
			for (Planet p:allPlanets){
			   p.draw();
			}
			StdDraw.show();
			StdDraw.pause(waitTimeMilliseconds);
		}
			
		
		StdOut.printf("%d\n", allPlanets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < allPlanets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  allPlanets[i].xxPos, allPlanets[i].yyPos, allPlanets[i].xxVel,
                  allPlanets[i].yyVel, allPlanets[i].mass, allPlanets[i].imgFileName);   
}
		
		
		
	}
}