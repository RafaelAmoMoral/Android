package com.example.clothes.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClotheDAO extends Clothe {

    static List<Clothe> mockClothes = new ArrayList<>();

    static {
        String image="iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAMAAAD04JH5AAAAclBMVEX///8AAADHx8f39/evr68XFxdfX193d3d/f3+Hh4efn5/f398KCgqzs7P7+/tycnK/v7+lpaUnJydUVFRPT0+Wlpbo6OjQ0NBjY2M3NzcZGRnc3Nzu7u4/Pz9GRkZra2uRkZE5OTkgICAYGBhDQ0MwMDAsPgwcAAAFGElEQVR4nO2b4ZaqIBSFYaxGJ0cdKzMzbboz7/+KNw6gCNhVseNad3V+qYjnizawAyNkSBxSOjrSw6BHDwlvMz49i403T/7se1p+Sr+zOfIfb1PzU3o7uudfheJhuc+jZieVb0TFrtfiJBeVwpVr/qD5NG/iSrS+n1zMOy/3y+tInLw11QKn9Kr8JACBJjDa9ggNIM9aACcpZid4xDruAMDDN/q9G/OeeA3VT5OlKOSXnledh5ObpWnhq7o1pwCwOqdOUhTyq0qiARzg+pd67xdcaoceDkDKykGKQn7xXVcaAG9umjdNm3HRK1+LACBRPFWK3o62H0oH8Lg2Qj8pCSkTnzfVSVGbBJDNRXcjpSjllxAbAMkKqfGiPVK11gKQZIoUj4WQH7EDkPOFanE5q+UKABFSLEZIsZVfH8BddoWavvjqlqoA46Xo84fWclizApAybls/LrXCDgCJan6fPyz/uyK/BwCMIbnm+TXRsxsAjRTfh+Tf8vZKlEt9AP2hA5CEf6vbAXVLuFPNPwcASeCxlsYyAyTTme7mAIBuUw2qnEHHVceuGQBgXF0PHAsgnzrhugPAZE0H90MY69N28HQG8GAsMqbw3ihhwm3chTsAjAO3QQpU6tPPuQA+6egH+B3NOAJwVQ8cBkVEJ7XXOAJAvz5FDysYcQ6V4dgNAIbh8Py4ghltNS9YTwNYB6wjdT7KmNizer+Rd+WGYgLAHeHqRb/sYD8+v5BO9YfS6QCU/qnGDIHd+KSdmAZAux16XNRzAdT/rmANPoDSsL5OA7jW3ASkk3+cvYUs/XZ6N9wyhHBUzW4c64DJx2EcyIJ6jjUCt5HwBbAEQMn0P3r4nxGA/bgYboCeAUC2M4jfCWDW6AeIvBEx0pIMADhWdFTsp8qyD2A/Lj+l+cwAY/PTYmmA9RMALgEErGnFgRawepbz498nAYg1hw92bGgMfpMLO7R5AfyvAIuLcPFu+AJYHCAeOhk/uRv+O14AL4AXgDOA/6HGsRcg2af32Bm8zgBa5D0AmdjuX+vLInMDsN0XG8BVlmv7aE4A8lOpEZZ2AF/eoC9NugAQuVjZRsGebwOI5R36drETAIm2WsDvPBsAM0GhrcANwB62PMybpYxAXxzEAmC/V0/sG9PXJ7AA2EbDD9vy0densQDY6uqeOeCfhQDYjvaG7VL+LgQA+mMLB+lCAOyab0uGBABb3wFb6A+XAYDN/wO8gqCtSSEBwCbxF2xVaxulSACwSPx5ENPlAgCw0ZTAi1XaUikSAE/NMRYBgIWKrbowgQwAhqjkUlwEAAxRdLZYIiSAGIag0mKJkABySBNZSpAAdnwaslgiJICKT8QWS4QE8M2tSGpaIiQAkdliiZAACt72FkuEBCDUZ7FESADikiUbDoAcgSyWCAdgK8ZgiyXCAZCzkMUS4QC8iXnYYolwAKQTsVgiHICVSGyxRDgAcufGYolwAMAQZa0Y0QHAEHmEWCwRDkAsBiCLJcIByEUWsETddyhxAJghgn+amJYIB6CS07BpiXAAmCGCt8JNS4QD0OQ1LREOQCFb/ud+cFoAoNGeaYlwAJorZjoUgHb8MS0RCgAYois7Mi0RCgDMQR/syLREKADaP7w6lggFoPUhpiVCAVg1aU1LhALQvspiWiIUAGmIbJboWQDf6l9/f4QhEh1yr5a5vEXzCMAMKCqtRSgAPElkLZv6Ol9fWF9ozB+UTX3Nvi+OZpZwJ0af84MyNf4C64pCGvNcv/cAAAAASUVORK5CYII=";
        mockClothes.add(new Clothe(1, "Vaquero", 6, "XS", "decription1", Calendar.getInstance(), true, "nuevo", image));
        mockClothes.add(new Clothe(2, "Camiseta", 6, "XS", "decription2", Calendar.getInstance(), true, "nuevo", null));
        mockClothes.add(new Clothe(3, "Gorra", 6, "XS", "decription3", Calendar.getInstance(), true, "nuevo", image));
        mockClothes.add(new Clothe(4, "Chaqueta", 6, "XS", "decription4", Calendar.getInstance(), true, "nuevo", null));
        mockClothes.add(new Clothe(5, "Zapatillas", 6, "XS", "decription5", Calendar.getInstance(), true, "nuevo", image));
        mockClothes.add(new Clothe(6, "Corbata", 6, "XS", "decription6", Calendar.getInstance(), true, "nuevo", null));
        mockClothes.add(new Clothe(7, "Suéter", 6, "XS", "decription7", Calendar.getInstance(), true, "nuevo", image));
        mockClothes.add(new Clothe(8, "Suéter", 6, "XS", "decription7", Calendar.getInstance(), true, "nuevo", image));
        mockClothes.add(new Clothe(9, "Suéter", 6, "XS", "decription7", Calendar.getInstance(), true, "nuevo", image));
        mockClothes.add(new Clothe(10, "Suéter", 6, "XS", "decription7", Calendar.getInstance(), true, "nuevo", image));
    }

    public List<Clothe> getAllClothes() {
        return mockClothes;
    }

}
