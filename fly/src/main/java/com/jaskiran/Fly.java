package com.jaskiran;

class Fly {
   private String departs;
   private String departsfrom;
   private String arrives;
   private String arrivesto;
    Fly(String departs,String departsfrom,String arrives,String arrivesto){
        this.departs= departs;
        this.departsfrom =departsfrom;
        this.arrives = arrives;
        this.arrivesto = arrivesto;
    };
    /**
     * @return the departs
     */
    public String getDeparts() {
        return departs;
    }
    public String getDepartsfrom() {
        return departsfrom;
    }
    public String getArrives() {
        return arrives;
    }  
    public String getArrivesto() {
        return arrivesto;
    }
    public void setDeparts(String departs) {
        this.departs = departs;
    }
    public void setDepartsfrom(String departsfrom) {
        this.departsfrom = departsfrom;
    }
    public void setArrives(String arrives) {
        this.arrives = arrives;
    }
    public void setArrivesto(String arrivesto) {
        this.arrivesto = arrivesto;
    }
    
    
    

}