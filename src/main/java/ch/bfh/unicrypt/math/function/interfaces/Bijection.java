/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.unicrypt.math.function.interfaces;

/**
 *
 * @author rolfhaenni
 */
public interface Bijection extends Injection {

    public Bijection invert();

}