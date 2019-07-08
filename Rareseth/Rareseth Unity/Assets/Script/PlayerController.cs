using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;

public enum E_PlayerType { Mage, Warrior }
public enum E_PlayerDirection { Left, Right, Up, Down }

[RequireComponent(typeof(Rigidbody2D))]
[RequireComponent(typeof(BoxCollider2D))]
public class PlayerController : MonoBehaviour
{

    #region Fields
    [Header("Player X-Axis Movement")]
    [SerializeField] private float m_XAxis;
    [Header("Player Y-Axis Movement")]
    [SerializeField] private float m_YAxis;
    [Header("Player Current Facing Direction?")]
    [SerializeField] private E_PlayerDirection m_PlayerDirection;
    [Header("Player Type")]
    [SerializeField] private E_PlayerType m_PlayerType;

    [Header("Speed of Player")]
    [SerializeField] private float m_PlayerSpeed = 5f;
    [Header("Player Current Movement Speed")]
    [SerializeField] private float m_CurrentRunSpeed;

    [Header("Is the Player Dead?")]
    [SerializeField] private bool m_IsDead = false;
    [Header("Is the Player Moving?")]
    [SerializeField] private bool m_IsMoving;
    [Header("Is the Player Moving?")]
    [SerializeField] private bool m_IsAttacking;
    [Header("Can the Player Move?")]
    [SerializeField] private bool m_CanMove = true;

    [Header("Player's RigidBody")]
    [SerializeField] private Rigidbody2D m_PlayerRigidbody;
    [SerializeField] private Animator m_PlayerAnimator;
    [SerializeField] private GameObject m_Mage;
    [SerializeField] private GameObject m_Warrior;
    [SerializeField] private int m_MageHealth;
    [SerializeField] private int m_WarriorHealth;

    #endregion

    #region Properties
    public float XAxis
    {
        get
        {
            return m_XAxis;
        }

        set
        {
            m_XAxis = value;
        }
    }
    public float YAxis
    {
        get
        {
            return m_YAxis;
        }

        set
        {
            m_YAxis = value;
        }
    }
    public E_PlayerDirection PlayerDirection
    {
        get
        {
            return m_PlayerDirection;
        }

        set
        {
            m_PlayerDirection = value;
        }
    }
    public float PlayerSpeed
    {
        get
        {
            return m_PlayerSpeed;
        }

        set
        {
            m_PlayerSpeed = value;
        }
    }
    public float CurrentRunSpeed
    {
        get
        {
            return m_CurrentRunSpeed;
        }

        set
        {
            m_CurrentRunSpeed = value;
        }
    }
    public bool IsDead
    {
        get
        {
            return m_IsDead;
        }

        set
        {
            m_IsDead = value;
        }
    }
    public bool IsMoving
    {
        get
        {
            return m_IsMoving;
        }

        set
        {
            m_IsMoving = value;
        }
    }
    public bool CanMove
    {
        get
        {
            return m_CanMove;
        }

        set
        {
            m_CanMove = value;
        }
    }
    public Rigidbody2D PlayerRigidbody
    {
        get
        {
            return m_PlayerRigidbody;
        }

        set
        {
            m_PlayerRigidbody = value;
        }
    }
    public bool IsAttacking
    {
        get
        {
            return m_IsAttacking;
        }

        set
        {
            m_IsAttacking = value;
        }
    }
    public E_PlayerType PlayerType
    {
        get
        {
            return m_PlayerType;
        }

        set
        {
            m_PlayerType = value;
        }
    }
    public GameObject Mage
    {
        get
        {
            return m_Mage;
        }

        set
        {
            m_Mage = value;
        }
    }
    public GameObject Warrior
    {
        get
        {
            return m_Warrior;
        }

        set
        {
            m_Warrior = value;
        }
    }

    public Animator PlayerAnimator
    {
        get
        {
            return m_PlayerAnimator;
        }

        set
        {
            m_PlayerAnimator = value;
        }
    }

    public int MageHealth
    {
        get
        {
            return m_MageHealth;
        }

        set
        {
            m_MageHealth = value;
        }
    }

    public int WarriorHealth
    {
        get
        {
            return m_WarriorHealth;
        }

        set
        {
            m_WarriorHealth = value;
        }
    }
    #endregion

    //Default Value in Inspector
    void Reset()
    {
        m_XAxis = 0;
        m_YAxis = 0;
        m_PlayerDirection = E_PlayerDirection.Down;
        m_PlayerType = E_PlayerType.Warrior;
        m_PlayerSpeed = 5f;
        m_CurrentRunSpeed = 0f;
        m_IsDead = false;
        m_IsMoving = false;
        m_CanMove = true;
        m_PlayerRigidbody = GetComponent<Rigidbody2D>();
        m_Warrior = GameObject.Find("Warrior");
        m_Warrior.SetActive(true);
        m_PlayerAnimator = m_Warrior.GetComponent<Animator>();
        m_Mage = GameObject.Find("Mage");
        m_Mage.SetActive(false);
        m_PlayerRigidbody.gravityScale = 0;
        m_IsAttacking = false;
    }

    // Update is called once per frame
    void Update()
    {
        GameObject.Find("Warrior Health").GetComponent<TextMeshProUGUI>().SetText("Warrior Health: " + m_WarriorHealth);
        GameObject.Find("Mage Health").GetComponent<TextMeshProUGUI>().SetText("Mage Health: " + m_MageHealth);

        if (m_MageHealth == 0 || m_WarriorHealth == 0) {
            GameObject.Find("Losing Text").GetComponent<TextMeshProUGUI>().enabled = true;
            Time.timeScale = 0;

        }

        switch (m_PlayerType)
        {
            case E_PlayerType.Mage:
                m_PlayerAnimator = m_Mage.GetComponent<Animator>();
                if (Input.GetKeyDown(KeyCode.X))
                {
                    m_PlayerType = E_PlayerType.Warrior;
                    m_Warrior.SetActive(true);
                    m_Mage.SetActive(false);
                }
                break;
            case E_PlayerType.Warrior:
                m_PlayerAnimator = m_Warrior.GetComponent<Animator>();
                if (Input.GetKeyDown(KeyCode.X))
                {
                    m_PlayerType = E_PlayerType.Mage;
                    m_Warrior.SetActive(false);
                    m_Mage.SetActive(true);
                }
                break;
        }

        transform.position += Vector3.zero;
        if (m_CanMove == true)
        {
            m_XAxis = Input.GetAxisRaw("Horizontal");
            m_YAxis = Input.GetAxisRaw("Vertical");
            m_PlayerAnimator.SetFloat("XInput", m_XAxis);
            m_PlayerAnimator.SetFloat("YInput", m_YAxis);

            m_PlayerAnimator.SetBool("IsAttacking", IsAttacking);

            if (Input.GetKeyDown(KeyCode.Space) && IsMoving == false)
            {
                StartCoroutine("Attack");

            }



            switch (m_PlayerDirection)
            {
                case E_PlayerDirection.Down:
                    m_PlayerAnimator.SetBool("FaceDown", true);
                    m_PlayerAnimator.SetBool("FaceUp", false);
                    m_PlayerAnimator.SetBool("FaceLeft", false);
                    m_PlayerAnimator.SetBool("FaceRight", false);
                    break;
                case E_PlayerDirection.Up:
                    m_PlayerAnimator.SetBool("FaceUp", true);
                    m_PlayerAnimator.SetBool("FaceDown", false);
                    m_PlayerAnimator.SetBool("FaceLeft", false);
                    m_PlayerAnimator.SetBool("FaceRight", false);
                    break;
                case E_PlayerDirection.Left:
                    m_PlayerAnimator.SetBool("FaceLeft", true);
                    m_PlayerAnimator.SetBool("FaceUp", false);
                    m_PlayerAnimator.SetBool("FaceDown", false);
                    m_PlayerAnimator.SetBool("FaceRight", false);
                    break;
                case E_PlayerDirection.Right:
                    m_PlayerAnimator.SetBool("FaceRight", true);
                    m_PlayerAnimator.SetBool("FaceUp", false);
                    m_PlayerAnimator.SetBool("FaceLeft", false);
                    m_PlayerAnimator.SetBool("FaceDown", false);
                    break;

            }

            //Set Direction
            #region SetDirection
            if (m_XAxis > 0)
            {
                m_PlayerDirection = E_PlayerDirection.Right;


            }
            else if (m_XAxis < 0)
            {
                m_PlayerDirection = E_PlayerDirection.Left;
            }
            else if (YAxis < 0)
            {
                m_PlayerDirection = E_PlayerDirection.Down;
            }
            else if (YAxis > 0)
            {
                m_PlayerDirection = E_PlayerDirection.Up;
            }
            #endregion

            if (Mathf.Abs(m_XAxis) > 0 || Mathf.Abs(YAxis) > 0)
            {
                m_IsMoving = true;
                m_PlayerAnimator.SetBool("IsMoving", m_IsMoving);
            }
            else
            {
                m_IsMoving = false;
                m_PlayerAnimator.SetBool("IsMoving", m_IsMoving);
            }

            m_PlayerRigidbody.velocity = new Vector2(m_XAxis * m_PlayerSpeed, m_YAxis * m_PlayerSpeed);
        }
    }

    IEnumerator Attack()
    {
        IsAttacking = true;

        yield return new WaitUntil(() => m_PlayerAnimator.GetCurrentAnimatorStateInfo(0).normalizedTime < 1);

        IsAttacking = false;
    }


}