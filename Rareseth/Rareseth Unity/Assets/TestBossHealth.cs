using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.Tilemaps;

public class TestBossHealth : MonoBehaviour {

    [SerializeField] private GameObject VictoryEntrance;
    [SerializeField] private GameObject VictoryCollider;
    [SerializeField] private int Health = 40;

	// Use this for initialization
	void Start () {
        VictoryEntrance = GameObject.Find("Victory Entrance");
        VictoryCollider = GameObject.Find("Middle Upper");
    }
	
	// Update is called once per frame
	void Update () {
        GameObject.Find("Boss Health").GetComponent<TextMeshProUGUI>().SetText("Boss Health: " + Health);
        if (Health == 0) {
            VictoryEntrance.GetComponent<TilemapRenderer>().enabled = true;
            VictoryCollider.GetComponent<BoxCollider2D>().enabled = false;
            Destroy(this.gameObject);
        }
	}

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.tag == "Sword")
        {
            Health -= 10;
        }
        if (collision.tag == "Ball")
        {
            Debug.Log("Damaged");
            GameObject ball = collision.gameObject;
            Health -= 5;
            ball.transform.position = new Vector2(0,0);
            GameObject.Find("Player").GetComponent<PlayerController>().PlayerAnimator.SetBool("IsAttacking", false);
            
        }
    }
}
